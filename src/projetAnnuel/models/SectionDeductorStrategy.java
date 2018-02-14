package projetAnnuel.models;

import java.util.ArrayList;

public interface SectionDeductorStrategy {
    ArrayList<TrackSection> deduceSections(Track track);
}
