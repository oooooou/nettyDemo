import serv.Serve;

public class te {
    public static void main(String args[]){
        Serve serve=new Serve("127.0.0.1",8546,false);
        serve.start();
    }
}
