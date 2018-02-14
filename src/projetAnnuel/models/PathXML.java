package projetAnnuel.models;

import java.util.Stack;

public class PathXML  extends Stack<String> {

    public boolean startsWith(PathXML path){
        int offset = this.size() - path.size();
        if(offset < 0) {
            return false;
        }
        for (int i = 0; i < path.size(); i++) {
            if (!path.get(i).equals(this.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean endsWith(PathXML path) {
        int offset = this.size() - path.size();
        if(offset < 0) {
            return false;
        }
        for (int ip = 0; ip < path.size(); ip++) {
            int ithis = ip + offset;
            if (!this.get(ithis).equals(path.get(ip))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.size(); i++) {
            result += "/" + this.get(i);
        }
        return result;
    }
}
