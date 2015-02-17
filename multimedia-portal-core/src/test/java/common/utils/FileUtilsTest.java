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

package common.utils;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileUtilsTest {
    private static final String TEST_FOLDER_NAME = "d:/projects/test";
    private static final String DIR_NAME = "asd";
    private File testFolder;

    @Before
    public void setUp() throws IOException {
        testFolder = new File(TEST_FOLDER_NAME);
        testFolder.mkdirs();
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteFiles(testFolder, true);
    }

    @Test
    public void createDirectory_noDir() throws IOException {
        assertEquals(getResultDirectoryPath(), 
                FileUtils.createDirectory(new FileSystemResource(getResultDirectoryFile())));
        assertTrue(getResultDirectoryFile().isDirectory());
    }

    @Test
    public void createDirectory_fileExists() throws IOException {
        File f = getResultDirectoryFile();
        f.createNewFile();
        assertEquals(getResultDirectoryPath(), 
                FileUtils.createDirectory(new FileSystemResource(getResultDirectoryFile())));
        assertTrue(getResultDirectoryFile().isDirectory());
    }

    @Test
    public void createDirectory_dirExists() throws IOException {
        File f = getResultDirectoryFile();
        f.mkdirs();
        assertEquals(getResultDirectoryPath(), 
                FileUtils.createDirectory(new FileSystemResource(getResultDirectoryFile())));
        assertTrue(getResultDirectoryFile().isDirectory());
    }

    public final String getResultDirectoryPath() throws IOException {
        return testFolder.getCanonicalPath() + File.separator + DIR_NAME; 
    }
    
    public final File getResultDirectoryFile() {
        return new File(testFolder, DIR_NAME);
    }

}
