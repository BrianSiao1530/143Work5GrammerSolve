import java.util.*;
public class GrammarSolver {
    private SortedMap<String, ArrayList<String>> nonterminal = new TreeMap<String, ArrayList<String>>();
    public GrammarSolver(List<String> grammar) {
        if (grammar.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Iterator<String> word = grammar.iterator();
        while(word.hasNext()){
            String[] line = (word.next()).split("::=");
            if(this.nonterminal.containsKey(line[0])){
                throw new IllegalArgumentException();
            }
            String[] insideLine = line[1].split("[|]");
            for(int i = 0; i <= insideLine.length - 1; i++) {
                if (!this.nonterminal.containsKey(line[0])) {
                    this.nonterminal.put(line[0], new ArrayList<>());
                    this.nonterminal.get(line[0]).add(insideLine[i]);
                } else {
                    this.nonterminal.get(line[0]).add(insideLine[i]);
                }
            }
        }
    }

    public boolean grammarContains(String symbol) {
        return (nonterminal.containsKey(symbol));
    }
    public String[] generate(String sybol, int times){
        if (times < 0 || !this.nonterminal.containsKey(sybol)){
            throw new IllegalArgumentException();
        }
        String[] out = new String[times] ;
        for (int i = 0; i < times; i++) {
            out[i] = generate(sybol);
        }
        return out;
    }
    public String generate(String sybol){
        String output = "";
        Random pickOne = new Random();
        ArrayList inside = nonterminal.get(sybol);
        int si = inside.size();
        String temp = "" + inside.get(pickOne.nextInt(si));
        if(temp.contains("<")) {
            String[] splitOfInside = temp.split("[ ]+");
            int sizeOfsep = splitOfInside.length;
            for (int i = 0; i <= sizeOfsep - 1; i++){
                if(nonterminal.containsKey(splitOfInside[i])){
                    output =  output + generate(splitOfInside[i]) ;
                }
            }
        }
        else {
            output = temp + " ";
        }
        return output;
    }
    public String getSymbols(){
        return this.nonterminal.keySet().toString();
    }
}
