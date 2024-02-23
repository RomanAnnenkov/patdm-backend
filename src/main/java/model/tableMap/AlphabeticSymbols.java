package model.tableMap;

import model.tableMap.IUniqueSymbols;

public class AlphabeticSymbols implements IUniqueSymbols {
    @Override
    public String[] getUniqueSymbols(int numberOfSymbols) {
        int maxIndex = 26;
        int charNumber = 65;
        String[] baseChars = new String[maxIndex];
        for (int i = 0; i < baseChars.length; i++) {
            baseChars[i] = Character.toString((char) charNumber++);
        }

        int lapBaseChars = 0;
        int indexBaseChar = 0;
        String[] uniqueSymbols = new String[numberOfSymbols];
        for (int i = 0; i < uniqueSymbols.length; i++) {
            if (lapBaseChars == 0) {
                uniqueSymbols[i] = baseChars[indexBaseChar];
            } else {
                uniqueSymbols[i] = baseChars[indexBaseChar] + baseChars[lapBaseChars - 1];
            }
            indexBaseChar++;
            if (indexBaseChar >= baseChars.length - 1) {
                indexBaseChar = 0;
                lapBaseChars++;
            }
        }

        return uniqueSymbols;
    }
}
