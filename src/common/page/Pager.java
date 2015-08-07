package common.page;

import java.util.List;

public class Pager<T>
{
   
    private int size;
   
    private int offset;
    
    private long total;
    
    private int pages;
    
    private List<T> datas;

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<T> getDatas()
    {
        return datas;
    }

    public void setDatas(List<T> datas)
    {
        this.datas = datas;
    }
    
    public void setPages(int pages)
    {
        this.pages = pages;
    }
    
    public int getPages()
    {
        if (total % size == 0)
        {
            this.pages = (int) (total / size);
        }
        else
        {
            this.pages = (int) (total / size) + 1;
        }
        
        return this.pages;
    }
}
