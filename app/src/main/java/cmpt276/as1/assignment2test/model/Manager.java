package cmpt276.as1.assignment2test.model;

import java.util.ArrayList;

public class Manager {
        private ArrayList<Lens> lens =new ArrayList<>();
        private static Manager instance;
        public static Manager getInstance(){
                if(instance==null){
                        instance=new Manager();
                }
                return instance;
        }
        public void add(Lens x){
                lens.add(x);
        }
        public void remove(int x){
                lens.remove(x);
        }
        public Lens get(int index){
                Lens ans=new Lens("error", 0, 0);
                for(int i=0;i<lens.size();i++){
                        if(i==index){
                                ans=(Lens)lens.get(i);
                        }
                }
                return ans;
        }
        public int getSize(){
                return lens.size();
        }

}
