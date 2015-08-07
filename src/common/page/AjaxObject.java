package common.page;

public class AjaxObject
{
    /**
     * Ajax的处理结果，0表示失败，1表示成功,-100表示当前未登录，-200表示无权限访问
     */
    private int result;
    
    /**
     * Ajax的处理之后的其他附加信息
     */
    private String message;
    
    /**
     * Ajax处理之后可能返回的对象
     */
    private Object object;

    public AjaxObject()
    {
    }

    public AjaxObject(int result)
    {
        super();
        this.result = result;
    }

    public AjaxObject(int result, Object object)
    {
        this.result = result;
        this.object = object;
    }

    public AjaxObject(int result, String message)
    {
        this.result = result;
        this.message = message;
    }

    public AjaxObject(int result, String message, Object object)
    {
        this.result = result;
        this.message = message;
        this.object = object;
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Object getObject()
    {
        return object;
    }

    public void setObject(Object object)
    {
        this.object = object;
    }
    
    
}
