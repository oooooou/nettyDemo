package serv;
import serv.Serve;
public class start {



        public static void main(String args[]){
            Serve serve=new Serve("120.78.223.5",8082,false);
            serve.start();
        }

}
