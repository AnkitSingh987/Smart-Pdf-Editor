package ankitsingh.smartpdfeditor.util;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class FileSortUtils {
    /*
     * This file is part of MyApplication.
     *
     * MyApplication is free software: you can redistribute it and/or modify
     * it under the terms of the GNU General Public License as published by
     * the Free Software Foundation, either version 3 of the License, or
     * (at your option) any later version.
     *
     * MyApplication is distributed in the hope that it will be useful,
     * but WITHOUT ANY WARRANTY; without even the implied warranty of
     * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
     * GNU General Public License for more details.
     *
     * You should have received a copy of the GNU General Public License
     * along with MyApplication. If not, see <https://www.gnu.org/licenses/>.
     */
    // Sorting order constants
    public final int NAME_INDEX = 0;
    public final int DATE_INDEX = 1;
    public final int SIZE_INCREASING_ORDER_INDEX = 2;
    public final int SIZE_DECREASING_ORDER_INDEX = 3;

    private FileSortUtils() {
    }

    public static FileSortUtils getInstance() {
        return FileSortUtils.SingletonHolder.INSTANCE;
    }

    // SORTING FUNCTIONS

    public void performSortOperation(int option, List<File> pdf) {
        switch (option) {
            case DATE_INDEX:
                sortFilesByDateNewestToOldest(pdf);
                break;
            case NAME_INDEX:
                sortByNameAlphabetical(pdf);
                break;
            case SIZE_INCREASING_ORDER_INDEX:
                sortFilesBySizeIncreasingOrder(pdf);
                break;
            case SIZE_DECREASING_ORDER_INDEX:
                sortFilesBySizeDecreasingOrder(pdf);
                break;
        }
    }

    /**
     * Sorts the given file list in increasing alphabetical  order
     *
     * @param filesList list of files to be sorted
     */
    private void sortByNameAlphabetical(List<File> filesList) {
        Collections.sort(filesList);
    }

    /**
     * Sorts the given file list by date from newest to oldest
     *
     * @param filesList list of files to be sorted
     */
    private void sortFilesByDateNewestToOldest(List<File> filesList) {
        Collections.sort(filesList, (file, file2) -> Long.compare(file2.lastModified(), file.lastModified()));
    }

    /**
     * Sorts the given file list in increasing order of file size
     *
     * @param filesList list of files to be sorted
     */
    private void sortFilesBySizeIncreasingOrder(List<File> filesList) {
        Collections.sort(filesList, (file1, file2) -> Long.compare(file1.length(), file2.length()));
    }

    /**
     * Sorts the given file list in decreasing order of file size
     *
     * @param filesList list of files to be sorted
     */
    private void sortFilesBySizeDecreasingOrder(List<File> filesList) {
        Collections.sort(filesList, (file1, file2) -> Long.compare(file2.length(), file1.length()));
    }

    private static class SingletonHolder {
        static final FileSortUtils INSTANCE = new FileSortUtils();
    }
}
