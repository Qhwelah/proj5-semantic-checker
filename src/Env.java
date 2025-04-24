import java.util.ArrayList;
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
        
        // // this is a fake implementation
        // // For the real implementation, I recommend to return a class object
        // //   since the identifier's type can be variable or function
        // //   whose detailed attributes will be different
        // if(name.equals("a") == true) return "num";
        // if(name.equals("b") == true) return "bool";
        // if(name.equals("testfunc") == true) return "num()";
        // return null;
    }
}
