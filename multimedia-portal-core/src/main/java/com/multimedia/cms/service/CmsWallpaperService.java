/*
 * Copyright 2012 demchuck.dima@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.multimedia.cms.service;

import com.multimedia.cms.wallpaper.WallpaperUploader;
import com.multimedia.security.model.User;
import com.multimedia.service.IPagesPseudonymService;
import com.multimedia.service.IPagesService;
import com.multimedia.service.IWallpaperService;
import common.cms.services2.AGenericCmsService;
import common.utils.FileUtils;
import gallery.model.active.PagesCombobox;
import gallery.model.beans.Pages;
import gallery.model.beans.Wallpaper;
import gallery.model.misc.StatusBean;
import gallery.model.misc.UploadBean;
import gallery.web.controller.pages.types.WallpaperGalleryType;
import gallery.web.support.wallpaper.Utils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("cmsWallpaperService")
public class CmsWallpaperService extends AGenericCmsService<Wallpaper, Long> implements ICmsWallpaperService{
	private final Logger logger = LoggerFactory.getLogger(CmsWallpaperService.class);
	private static final List<String> CMS_SHORT_ALIAS = Arrays.asList("id","name","id_pages","active", "optimized", "width", "height", "folder");

    @Autowired
    private IPagesService pagesService;
    @Autowired
    private IPagesPseudonymService pagesPseudoService;
    
    private IWallpaperService wallpaperService;

    @Autowired
    public void setWallpaperService(IWallpaperService wallpaperService) {
        this.wallpaperService = wallpaperService;
        this.service = wallpaperService;
    }

    private static final int UPLOAD_BATCH_SIZE = 25;
    private static final int MOVE_TO_ROOT_BATCH_SIZE = 1;
    private static final int MOVE_TO_ROOT_MAX_COUNT = 1000;

	//-------------------------------------------- overridden common cms methods ----------------
	private static final String[] CMS_EDIT_NAMES = {"active", "optimized"};
	@Override
	public int saveOrUpdateCollection(Collection<Wallpaper> c) {
		return wallpaperService.updateCollection(c, CMS_EDIT_NAMES);
	}

	@Override
	public Wallpaper getInsertBean(Wallpaper obj) {
	    Wallpaper w;
		if (obj==null) {
			w = new Wallpaper();
		} else {
		    w = obj;
		}
		w.setActive(true);
		return w;
	}

	@Override
	public int update(Wallpaper command) {
		if (command.getContent() == null) {
			if (wallpaperService.renameFiles(command)) {
				//we just need to update rows in database
				wallpaperService.save(command);
			}
		} else {
			//1-st delete old wallpapers
			if (wallpaperService.deleteFiles(command) && wallpaperService.getImage(command)) {
				//2-nd update rows in database, create new, and count new resolution
				wallpaperService.save(command);
			} else {
				return -1;
			}
		}
		return 1;
	}

	private static final String[] CONTENT_FIELDS = {"width", "height", "date_upload", "type"};
	@Override
	public int update(Wallpaper command, String... names) {
		if (command.getContent() == null) {
			if (wallpaperService.renameFiles(command)) {
				//we just need to update rows in database
				wallpaperService.update(command, names);
			}
		} else {
			//1-st delete old wallpapers
			if (wallpaperService.deleteFiles(command) && wallpaperService.getImage(command)) {
				//2-nd update rows in database, create new, and count new resolution
				wallpaperService.update(command, names);
				//3-rd updating fields that have changed because of file
				wallpaperService.update(command, CONTENT_FIELDS);
			} else {
				return -1;
			}
		}
		return 1;
	}

	@Override
	public boolean insert(Wallpaper obj) {
        initWallpaper(obj);
		if (wallpaperService.getImage(obj)) {
			wallpaperService.save(obj);
			return true;
		} else {
			return false;
		}
	}

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public int insert(List<Wallpaper> wallpapers, List<File> resizedFolder) {
        for (Wallpaper wallpaper : wallpapers) {
            initWallpaper(wallpaper);
        }
		if (wallpaperService.getImages(wallpapers, resizedFolder)) {
            logger.info("Uploading {} files", wallpapers.size());
			return wallpaperService.save(wallpapers).size();
		} else {
			return 0;
		}
	}

    @Override
    public int insert(List<Wallpaper> list) {
        int rez = 0;
        for (Wallpaper obj : list) {
            if (insert(obj)) {
                rez++;
            }
        }
        return rez;
    }

    /** prepares entity before insert. */
	private void initWallpaper(Wallpaper entity) {
        if (entity.getDescription()==null) {
            entity.setDescription(entity.getName());
        }
        if (entity.getTitle()==null) {
            entity.setTitle(entity.getName());
        }
        if (entity.getTags()==null) {
            entity.setTags(entity.getName());
        }
        if (entity.getViews() == null) {
            entity.setViews(0L);
        }
        if (entity.getOptimized() == null) {
            entity.setOptimized(Boolean.FALSE);
        }
        if (entity.getOptimized_manual() == null) {
            entity.setOptimized_manual(Boolean.FALSE);
        }
        wallpaperService.normalizeFolder(entity);
        if (!StringUtils.hasText(entity.getFolder())) {
            entity.setFolder(wallpaperService.defaultFolderName());
        }
	}

	@Override
	public void initUpdate(Map<String, Object> model) {
		model.put("categories_wallpaper_select", pagesService.getAllCombobox(null, Boolean.TRUE, WallpaperGalleryType.TYPE));
	}

	@Override
	public void initInsert(Map<String, Object> model) {
		model.put("categories_wallpaper_select", pagesService.getAllCombobox(null, Boolean.TRUE, WallpaperGalleryType.TYPE));
	}

	@Override
	public void initView(Map<String, Object> model) {
		model.put("categories_wallpaper_select", new PagesCombobox(null, pagesService));
	}

	@Override
	public List<String> getListPropertyNames() {return CMS_SHORT_ALIAS;}

	@Override
	public List<String> getListPropertyAliases() {return CMS_SHORT_ALIAS;}
//-------------------------------------------- wallpaper specific methods -------------------

	/** name of file with  */
	private static final String DESCRIPTION_FILE = "name.txt";
	@Override
	public List<UploadBean> createPageFolders() {
		//getting layered pages
		List<Pages> pages = getCategoriesLayered();
		//creating vector for results
		LinkedList<UploadBean> rez = new LinkedList<UploadBean>();
		//creating an upload dir if it doesn't exists
		File uploadDir = new File(wallpaperService.getUploadPath());
		if (!uploadDir.exists()) {
		    uploadDir.mkdirs();
		}
		//for saving parent catalogues
		Long lastLayer = -1L;
		LinkedList<File> parents = new LinkedList<File>();
		parents.addLast(uploadDir);
		for (Pages p:pages) {
			while (p.getLayer() <= lastLayer) {
				//removing last parent
				parents.removeLast();
				lastLayer--;
			}
			File curDir = new File(parents.getLast(), String.valueOf(p.getId())+'_'+FileUtils.toTranslit(p.getName()));
			if (curDir.exists()) {
				if (!curDir.isDirectory()) {
					curDir.delete();
					curDir.mkdir();
				}
			} else {
				curDir.mkdir();
			}
			createDescriptionFile(curDir, p.getId(), p.getName(), false);

			UploadBean ub = new UploadBean();
			ub.setId(p.getId());
			ub.setFolder_name(curDir.getName());
			ub.setPage_name(p.getName());
			ub.setItem_count(curDir.listFiles((FileFilter)FileFileFilter.FILE).length-1);
			rez.addLast(ub);

			parents.addLast(curDir);
			lastLayer = p.getLayer();
		}
		return rez;
	}

	/**
	 * scans upload folder for files
	 * and returns total quantity of files except description file
	 * @param baseDir directory where to scan
	 */
	public long scanFolder(File baseDir) {
		File[] files = baseDir.listFiles();
		long count = 0L;
		for (File file:files){
			if (file.isDirectory()){
				count += scanFolder(file);
			} else if (!DESCRIPTION_FILE.equals(file.getName())){
				count++;
			}
		}
		return count;
	}

	private static final String[] RUBRIC_WHERE = {"type"};
	private static final String[] RUBRIC_PSEUDONYMES = {"id","id_pages","name","type","last"};
	@Override
	public List<Pages> getCategoriesLayered() {
        return pagesService.getPagesChildrenRecurciveOrderedWhere(RUBRIC_PSEUDONYMES, RUBRIC_WHERE,
				new Object[][]{new String[]{WallpaperGalleryType.TYPE}});
	}

    //TODO: refactor, include wallpaper folder
	@Override
	public long uploadWallpapers(User user, Boolean active, Long id_pages, StatusBean usb) {
		File uploadDir = new File(wallpaperService.getUploadPath());
        usb.setDone(0L);
		usb.setTotal(scanFolder(uploadDir));
		if (uploadDir.exists()) {
			Long id_pages_cur;
			LinkedList<File> files = new LinkedList<File>();
			files.addLast(uploadDir);

            WallpaperUploader uploader = new WallpaperUploader(UPLOAD_BATCH_SIZE, user, id_pages, usb,
                    wallpaperService.getDimmensions().keySet());

			while (!files.isEmpty()) {
				File curFolder = files.removeLast();
				boolean preUploaded = false;
				if (curFolder.isDirectory()) {
					//search for DESCRIPTION_FILE
					File descriptionFile = new File(curFolder, DESCRIPTION_FILE);
					if (descriptionFile.exists()) {
						id_pages_cur = null;
                        BufferedReader reader = null;
                        try {
                            reader = new BufferedReader(new InputStreamReader(new FileInputStream(descriptionFile), "UTF-8"));
                            String line;
							while ((line=reader.readLine())!=null) {
								if (line.startsWith("id=")) {
									id_pages_cur = Long.parseLong(line.substring(3), 10);
								} else if (line.startsWith("pre_uploaded=true")) {
									//means that this folder contains subfolders with pre uploaded images
									//i.e. wallpapers are already resized and are stored in an appropriate folders
									//but they still must be checked
									preUploaded = true;
								}
							}
						} catch (IOException ex) {
							logger.error("", ex);
						} finally {
                            IOUtils.closeQuietly(reader);
                        }
					} else {
						id_pages_cur = id_pages;
					}
					File[] filesTemp = curFolder.listFiles();
					for (File tmp : filesTemp) {
						if (tmp.isFile()) {
							if (!tmp.getName().equals(DESCRIPTION_FILE) && id_pages_cur!=null) {
                                uploader.wallpaper(active, id_pages_cur, tmp);
								logger.trace("normal file uploading: [{}]", tmp.getAbsolutePath());
                                uploader.upload(this);
							}//else error
						} else if (!preUploaded) {
							files.addLast(tmp);
						}
					}

					if (preUploaded) {
						//uploading pre_uploaded files if any
						File preUploadedFolder = new File(curFolder, Utils.FULL_DIMENSION_NAME);
						if (preUploadedFolder.exists()&&preUploadedFolder.isDirectory()) {
							filesTemp = preUploadedFolder.listFiles((FileFilter)FileFileFilter.FILE);
							for (File tmp : filesTemp) {
                                uploader.preUploadedWallpaper(active, id_pages_cur, tmp, curFolder);
                                logger.trace("prepared file uploading: [{}]", tmp.getAbsolutePath());
                                uploader.upload(this);
							}
							//deleting pre_uploaded folder if it contains no images
							if (preUploadedFolder.listFiles((FileFilter)FileFileFilter.FILE).length == 0) {
								FileUtils.deleteFiles(curFolder, true);
							}
						}
					}
				}
			}
            uploader.finish(this);
		}
		return usb.getDone();
	}

    //TODO: refactor, include wallpaper folder to descriptor file
	@Override
	public void preUploadWallpapers(StatusBean usb) {
		File uploadDir = new File(wallpaperService.getUploadPath());
        usb.setDone(0L);
		usb.setTotal(scanFolder(uploadDir));
		if (uploadDir.exists()) {
			boolean uploadMade = true;
			int uploadCount = 0;

			File preUploadDirectory = new File(wallpaperService.getUploadPath(), "pre_upload");
			if (!preUploadDirectory.exists() ) {
				preUploadDirectory.mkdir();
			}

			File curDir = null;

			File descriptionFile;
			boolean preUploaded;

			Long id_pages_cur;
			String pageName;

			LinkedList<File> files = new LinkedList<File>();
			files.addLast(uploadDir);

			while (!files.isEmpty()) {
				if (uploadMade) {
					curDir = new File(preUploadDirectory, String.valueOf(uploadCount));
					while (curDir.exists()) {
						curDir = new File(preUploadDirectory, String.valueOf(uploadCount));
						uploadCount++;
					}
					curDir.mkdir();
                    for (String dimmension : wallpaperService.getDimmensions().keySet()) {
                        File preUploadedDimm = new File(curDir, dimmension);
                        if (!preUploadedDimm.exists()) {
                            preUploadedDimm.mkdir();
                        }
                    }
					uploadCount++;
				}

				File f = files.removeLast();
				preUploaded = false;
				uploadMade = false;
				if (f.isDirectory()) {
					id_pages_cur = null;
					pageName = null;
					//search for DESCRIPTION_FILE
					descriptionFile = new File(f, DESCRIPTION_FILE);
					if (descriptionFile.exists()) {
                        id_pages_cur = null;
                        BufferedReader reader = null;
                        try {
                            reader = new BufferedReader(new InputStreamReader(new FileInputStream(descriptionFile), "UTF-8"));
                            String line;
							while ((line=reader.readLine())!=null) {
								if (line.startsWith("id=")) {
									id_pages_cur = Long.parseLong(line.substring(3), 10);
								} else if (line.startsWith("name=")) {
									pageName = line.substring(5);
								} else if (line.startsWith("pre_uploaded=true")) {
									//means that this folder contains subfolders with pre uploaded images
									//i.e. wallpapers are already resized and are stored in an appropriate folders
									//but they still must be checked
									preUploaded = true;
								}
							}
						} catch (IOException ex) {
							logger.error("", ex);
						} finally {
                            IOUtils.closeQuietly(reader);
                        }
					}

					File[] filesTemp = f.listFiles();

					for (File tmp:filesTemp) {
						if (tmp.isFile()) {
							if (!tmp.getName().equals(DESCRIPTION_FILE)&&id_pages_cur!=null) {
								usb.setCur_name(tmp.getAbsolutePath());
								logger.debug("preparing upload file: [{}]", tmp.getAbsolutePath());

								if (Utils.saveScaledImageFileToDisk(tmp, wallpaperService.getDimmensions(), curDir)) {
									tmp.delete();
									usb.increaseDone(1L);
									uploadMade = true;
								}
							}//else error
						} else if (!preUploaded) {
							files.addLast(tmp);
						}
					}
					//create a description file
					if (uploadMade) {
						createDescriptionFile(curDir, id_pages_cur, pageName, true);
						curDir = null;
					}
				}
			}
			if (curDir!=null) {
				descriptionFile = new File(curDir, DESCRIPTION_FILE);
				if (!descriptionFile.exists()) {
					FileUtils.deleteFiles(curDir, true);
				}
			}
		}
	}

    //TODO: refactor, include wallpaper folder, add folder to description
	protected boolean createDescriptionFile(File cur_dir, Long page_id, String page_name, boolean prepared){
		File description = new File(cur_dir, DESCRIPTION_FILE);
		if (!description.exists()) {
            try {
                if (!description.createNewFile()) {
                    logger.error("Failed to create description file [{}]", description.getAbsolutePath());
                    return false;
                }
            } catch (IOException e) {
                logger.error("Failed to create description file [{}]", description.getAbsolutePath());
                return false;
            }
            OutputStreamWriter fos = null;
            try {
                fos = new OutputStreamWriter(new FileOutputStream(description), "UTF-8");
                fos.write("id=");
				fos.write(String.valueOf(page_id));
				fos.write("\r\nname=");
				fos.write(page_name);
				fos.write("\r\nname_translit=");
				fos.write(FileUtils.toTranslit(page_name));
				if (prepared) {
					fos.write("\r\npre_uploaded=true");
				}
				fos.close();
			} catch (IOException ex) {
				logger.error("", ex);
				return false;
			} finally {
                IOUtils.closeQuietly(fos);
            }
		}
		return true;
	}

	@Override
	public int deleteById(Long[] id) {
        return wallpaperService.deleteById(id);
    }

	private static final String[] WALLPAPER_ID_PAGES = {"id_pages"};
	@Override
	public List<Long> getWallpapersInCategory(Long id) {
		if (id==null){
			return wallpaperService.getSingleProperty("id", null, null, 0, 0, null, null);
		} else {
			return wallpaperService.getSingleProperty("id", WALLPAPER_ID_PAGES, new Object[]{id}, 0, 0, null, null);
		}
	}

	private static final String[] RESIZE_PSEUDONYMES = {"id", "name", "folder"};
	@Override
	public boolean reResizeWallpapers(boolean appendAll) {
		List<Wallpaper> wallpapers = wallpaperService.getOrdered(RESIZE_PSEUDONYMES, null, null);
        return Utils.resizeWallpaper(wallpapers, wallpaperService.getDimmensions(), wallpaperService.getStorePath(), appendAll);
	}

	private static final String[] PAGES_PROP_NAMES = {"id", "id_pages", "name"};
	private static final String[] PAGES_PSEDO_WHERE = {"id_pages", "useInItems"};
	@Override
	public void optimizeWallpaper(Long id) {
		Wallpaper p = wallpaperService.getById(id);
		if (p.getOptimized_manual() || p.getOptimized()) {
			return;
		}
		List<Pages> parents = pagesService.getAllPagesParents(p.getId_pages(), PAGES_PROP_NAMES);
		//selecting random optimization phrase(page pseudonym) for each parent
		StringBuilder title = new StringBuilder();
		String name = null;
		StringBuilder tags = new StringBuilder();
		String rop = null;
		Random r = new Random();

        int size = parents.size()-1;
		for (int i=0;i<size;i++) {
			Object[] whereVals = {parents.get(i).getId(), Boolean.TRUE};
			Long count = pagesPseudoService.getRowCount(PAGES_PSEDO_WHERE, whereVals);
			if (count>0L) {
				//random optimization phrase
				String ROP = (String) pagesPseudoService.getSinglePropertyU("text", PAGES_PSEDO_WHERE, whereVals, r.nextInt(count.intValue()));
				if (ROP!=null&&!ROP.isEmpty()) {
					title.append(ROP);title.append(" - ");
					rop = ROP;
				}
				tags.append(parents.get(i).getName());tags.append(", ");
			}
		}
		size++;
        if (size>0) {
			size--;
			Object[] where_vals = {parents.get(size).getId(), Boolean.TRUE};
			Long count = pagesPseudoService.getRowCount(PAGES_PSEDO_WHERE, where_vals);
			if (count>0L) {
				//random optimization phrase
				String ROP = (String) pagesPseudoService.getSinglePropertyU("text", PAGES_PSEDO_WHERE, where_vals, r.nextInt(count.intValue()));
				if (ROP!=null&&!ROP.isEmpty()) {
					title.append(ROP);
					name=ROP;
				} else if (rop!=null) {
					name=rop;
				} else {
					name=parents.get(size).getName();
				}
				tags.append(parents.get(size).getName());
			}
		}

		p.setDescription(name);
		p.setTags(tags.toString());
		p.setTitle(title.toString());
		p.setOptimized(Boolean.TRUE);
        wallpaperService.save(p);
	}

	private static final String[] WALLPAPER_OPTIMIZED_CATEGORY_WHERE = {"id_pages", "optimized", "optimized_manual"};
	@Override
	public void optimizeWallpaperCategories(Long[] id_pages) {
		HashMap<Long, Pages> page_ids = new HashMap<Long, Pages>();
		List<Wallpaper> wallpapers;
		StringBuilder title;
		String name;
		StringBuilder tags;
		Random r = new Random();
		Pages page;
		int layer;

        for (Long id_page : id_pages) {
            wallpapers = wallpaperService.getByPropertiesValueOrdered(null, WALLPAPER_OPTIMIZED_CATEGORY_WHERE, new Object[]{id_page, Boolean.FALSE, Boolean.FALSE}, null, null);
            if (!wallpapers.isEmpty()) {
                //getting all parents with optimization
                Long id_pages_tmp = id_page;
                while (id_pages_tmp != null && !page_ids.containsKey(id_pages_tmp)) {
                    Object[] page_a = (Object[]) pagesService.getSinglePropertyU("id, id_pages, name", "id", id_pages_tmp);
                    Pages page_o = new Pages();
                    page_o.setId((Long) page_a[0]);
                    page_o.setId_pages((Long) page_a[1]);
                    page_o.setName((String) page_a[2]);
                    page_o.setPseudonyms(
                            pagesPseudoService.getByPropertiesValueOrdered(null, PAGES_PSEDO_WHERE, new Object[]{page_o.getId(), Boolean.TRUE}, null, null)
                    );
                    page_ids.put(page_o.getId(), page_o);
                    id_pages_tmp = page_o.getId_pages();
                }

                for (Wallpaper p : wallpapers) {
                    title = new StringBuilder();
                    name = null;
                    tags = new StringBuilder();
                    layer = 0;
                    boolean rop_set = false;
                    id_pages_tmp = id_page;
                    while (id_pages_tmp != null) {
                        page = page_ids.get(id_pages_tmp);
                        if (!page.getPseudonyms().isEmpty()) {
                            int num = r.nextInt(page.getPseudonyms().size());
                            String ROP = page.getPseudonyms().get(num).getText();
                            if (ROP != null && !ROP.isEmpty()) {
                                if (!rop_set) {
                                    name = ROP;
                                    rop_set = true;
                                } else {
                                    title.insert(0, " - ");
                                }
                                title.insert(0, ROP);
                            } else if (layer == 0) {
                                name = page.getName();
                            }

                            if (layer != 0) {
                                tags.insert(0, ", ");
                            }
                            tags.insert(0, page.getName());
                            layer++;
                        }

                        id_pages_tmp = page.getId_pages();
                    }

                    p.setDescription(name);
                    p.setTags(tags.toString());
                    p.setTitle(title.toString());
                    p.setOptimized(Boolean.TRUE);
                    wallpaperService.save(p);
                }
            }
        }
	}

	private static final String[] OPTIMIZATION_CLEAR1 = {"optimized"};
	@Override
	public void setWallpaperOptimizationCategories(Long[] id_pages, Boolean optimized) {
		wallpaperService.updateObjectArrayShortByProperty(OPTIMIZATION_CLEAR1, new Object[]{optimized}, "id_pages", id_pages);
	}

//	private final String[] WALLPAPER_RESOLUTION = new String[]{"width", "height"};
	@Override
	public long renewResolution(StatusBean sb) {
	    throw new UnsupportedOperationException("Needs to ne re-implemented.");
//		sb.setTotal((Long)wallpaper_service.getSinglePropertyU("count(*)"));
//		ScrollableResults sr = wallpaper_service.getScrollableResults("id, name, folder", null, null, null, null);
//		File img_dir = new File(wallpaper_service.getStorePath(), Utils.FULL_DUMENSION_DIR);
//
//		sr.beforeFirst();
//      Wallpaper wallpaper = new Wallpaper();
//		while (sr.next()) {
//          wallpaper.setName(sr.getString(1));
//          wallpaper.setFolder(sr.getString(2));
//          File f = Utils.findFileFromResolution(img_dir, wallpaper);
//			try {
//				BufferedImage bi = ImageUtils.readImage(f).getImage();
//				wallpaper_service.updateObjectArrayShortByProperty(WALLPAPER_RESOLUTION, new Object[]{bi.getWidth(), bi.getHeight()}, "id", new Object[]{sr.getLong(0)});
//				sb.increaseDone(1);
//			} catch (Exception ex) {
//				logger.error("while trying to read wallpaper's resolution id = " + sr.getLong(0), ex);
//			}
//		}
//		return sb.getDone();
	}

	@Override
	public List<List<Wallpaper>> findWallpaperDuplicates(int quantity) {
		List<Wallpaper> wallpapers = wallpaperService.getOrdered(null, null, null);
		List<List<Wallpaper>> result = new LinkedList<List<Wallpaper>>();
		Set<Long> processedWallpapers = new HashSet<Long>();

        File fullImagesDir = new File(wallpaperService.getStorePath(), Utils.FULL_DIMENSION_NAME);
		for (int i = 0; i < wallpapers.size(); i++) {
            Wallpaper wallpaperObj = wallpapers.get(i);
			File wallpaperFile = Utils.findFileFromResolution(fullImagesDir, wallpaperObj);
			if (!processedWallpapers.contains(wallpaperObj.getId())) {
				if (wallpaperFile.exists()) {
                    List<Wallpaper> duplicates = null;
					for (int j = i + 1; j < wallpapers.size(); j++) {
						Wallpaper curItem = wallpapers.get(j);
						if (curItem.getWidth().equals(wallpaperObj.getWidth()) &&
							curItem.getHeight().equals(wallpaperObj.getHeight()))
						{
							File curFile = Utils.findFileFromResolution(fullImagesDir, curItem);
							if (wallpaperFile.length() == curFile.length()) {
								if (duplicates == null) {
									duplicates = new LinkedList<Wallpaper>();
									processedWallpapers.add(wallpaperObj.getId());
									duplicates.add(wallpaperObj);
								}
								processedWallpapers.add(curItem.getId());
								duplicates.add(curItem);
							}
						}
					}
					if (duplicates != null && duplicates.size() > 1) {
						result.add(duplicates);
					}
				} else {
					result.add(Arrays.asList(wallpaperObj));
				}
				if (quantity > 0 && result.size() > quantity) {
					break;
				}
			}
		}
		return result;
	}

	@Override
	public int moveWallpapersToPage(Long[] id, Long id_pages_new) {
		Object pageType = pagesService.getSinglePropertyU("type", "id", id_pages_new);
		if (WallpaperGalleryType.TYPE.equals(pageType)) {
			return wallpaperService.updatePropertiesById(WALLPAPER_ID_PAGES, new Object[]{id_pages_new}, id);
		} else {
			return -1;
		}
	}

    @Override
    public List<String> moveWallpaperImagesFromRootToChild() {
        ArrayList<String> result = new ArrayList<String>();
        int countOk = 0;
        int countErr = 0;
        for (int i = 0; i < MOVE_TO_ROOT_MAX_COUNT; i++) {
            for (Wallpaper wallpaper : wallpaperService.findByFolder("", MOVE_TO_ROOT_BATCH_SIZE)) {
                Wallpaper newWallpaper = new Wallpaper();
                newWallpaper.setId(wallpaper.getId());
                newWallpaper.setName(wallpaper.getName());
                newWallpaper.setFolder(wallpaperService.defaultFolderName());
                if (!wallpaperService.renameFiles(newWallpaper, wallpaper) ||
                        wallpaperService.update(newWallpaper, "folder") != 1) {
                    logger.error("Failed to move wallpaper [{}]", wallpaper.getId());
                    result.add("Error at wallpaper [" + wallpaper.getId() + "].");
                    countErr += 1;
                } else {
                    countOk += 1;
                }
            }
        }
        result.add(0, "Moved [" + countOk + "] elements, [" + countErr + "] errors.");
        return result;
    }
}