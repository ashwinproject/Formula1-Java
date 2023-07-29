package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;


public interface ChampionshipManager {
    void tableFill() throws FileNotFoundException;

    void load() throws FileNotFoundException;

    void launchRace() throws IOException;

    void saveData() throws IOException;

    void resetValues() throws IOException;

    void ascOrder() throws IOException;

    void decOrder() throws IOException;

    void fposCount() throws IOException;

    void srchBar();

    void raceHistory();

    void boostRace() throws IOException;
}
