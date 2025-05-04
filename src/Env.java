import java.util.HashMap;

public class Env
{
    public Env prev;
    private HashMap<String, Object> theEnv; 

    public Env(Env prevEnv)
    {
        theEnv = new HashMap<String, Object>();
        prev = prevEnv;
    }
    public void Put(String name, Object value)
    {
        theEnv.put(name, value);
    }
    public Object Get(String name)
    {
        if(theEnv.get(name) != null){
            return theEnv.get(name);
        }
        else{
            if(prev != null){
                return(prev.Get(name));
            }
            else {
                return(null);
            }
        }
    }
    public boolean isDuplicate(String name){
        if(theEnv.get(name) != null) {return true;} // only checks top table
        else return false;
    }
}
