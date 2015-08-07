package com.common.page;


/**
 * User: david
 * Date: 2010-8-30
 * Time: 0:41:25
 */
public class PageUtil {

    private static final int defaultMaxLinkedPages = 5;
    
    /**
     * Return the first page to which create a link around the current page.
     */
    public static int getFirstLinkedPage(int currentPage, int pageCount, int maxLinkedPages) {
        return Math.max(0, Math.min(pageCount - maxLinkedPages, currentPage - maxLinkedPages / 2));
    }

    public static int getFirstLinkedPage(int currentPage, int pageCount) {
        return Math.max(0, Math.min(pageCount - defaultMaxLinkedPages, currentPage - defaultMaxLinkedPages / 2));
    }

    /**
     * Return the last page to which create a link around the current page.
     */
    public static int getLastLinkedPage(int currentPage, int pageCount, int maxLinkedPages) {
        return Math.min(getFirstLinkedPage(currentPage, pageCount, maxLinkedPages) + maxLinkedPages - 1, pageCount - 1);
    }

    public static int getLastLinkedPage(int currentPage, int pageCount) {
        return Math.min(getFirstLinkedPage(currentPage, pageCount, defaultMaxLinkedPages) + defaultMaxLinkedPages - 1, pageCount - 1);
    }

    public static int getMaxPage(int totalCount, int pageSize) {
        return totalCount%pageSize==0 ? totalCount/pageSize : totalCount/pageSize + 1;
    }
    
    /**
     * default Page
     */
    public static Page getDefaultPage(){
    	return new Page();
    }
}
