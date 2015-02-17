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

package common.services.generic;

import common.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @param <T> 
 * @author demchuck.dima@gmail.com
 */
public abstract class GenericCacheService<T> implements IGenericCacheService<T> {
    private final Logger logger = LoggerFactory.getLogger(GenericCacheService.class);
    /* path to folder where the cache will be stored */
    private final String path;
    private T cachedObject;

    protected GenericCacheService(Resource path) {
        this.path = FileUtils.createDirectory(path, "Cache folder not found.");
    }

    @Override
    public synchronized void clearCache() {
		File f = new File(path);
		File[] files = f.listFiles();
		for (File file:files){
			file.delete();
		}
		cachedObject = null;
    }

    @Override
    public synchronized void refreshCache(){
        cachedObject = getFromDB();
        saveToFile();
    }

    public synchronized T getObject() {
		if (cachedObject!=null){
			//logger.info("rubrication from class");
			return cachedObject;
		} else {
            cachedObject = getFromFile();
            if (cachedObject!=null){
                //logger.info("rubrication from file");
                return cachedObject;
            } else {
                cachedObject = getFromDB();
                if (cachedObject!=null){
                    //logger.info("rubrication from DB");
                    saveToFile();
                    return cachedObject;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public T getFromFile() {
        File f = new File(path,"cache.tmp");
        if (!f.exists()) {
            return null;
        }
        try {
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream si = new ObjectInputStream(fi);
            T tmp = (T)si.readObject();
            si.close();
            fi.close();
            return tmp;
        } catch (IOException ex) {
            logger.error("failed to load cache path = "+path,ex);
            return null;
        } catch (ClassNotFoundException ex) {
            logger.error("failed to load cache path = "+path,ex);
            return null;
        }
    }

    @Override
    public synchronized boolean saveToFile(){
        if (cachedObject==null) {
            return false;
        }
        try {
            File f = new File(path,"cache.tmp");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            ObjectOutputStream so = new ObjectOutputStream(fo);
            so.writeObject(cachedObject);
            so.flush();
            so.close();
            fo.close();
            return true;
        } catch (IOException ex) {
            logger.error("failed to save cache path = "+path,ex);
            return false;
        }
    }
}
