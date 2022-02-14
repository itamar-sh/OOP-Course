package Q2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class USSCandidateSelection implements CandidateSelection{
    private Collection requirments;
    public USSCandidateSelection(Collection requirments){
        this.requirments = requirments;
    }
    @Override
    public List<Cadet> getCadets(List<Cadet> candidates){
        List<Cadet> cadets = new ArrayList<>();
        for(Cadet cadet : candidates){
            List<String> skills = cadet.getSkills();
            if (cadet.getHeight() < 1.65 & skills.contains(Arrays.asList(this.requirments))){
                cadets.add(cadet);
            }
        }
        return cadets;
    }



    private static List<Cadet> teleportCadets() {
        List<Cadet> cadets = new ArrayList<>();
        return cadets;
    }


}
