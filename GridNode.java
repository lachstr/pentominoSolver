import java.util.ArrayList;

public class GridNode {

    public char[][] grid;
    ArrayList<Character> avaliableTypes = new ArrayList<>();

    public GridNode(char[][] grid, ArrayList<Character> avaliableTypes) {

        for (Character c : avaliableTypes){
            this.avaliableTypes.add(c);
        }
        this.grid = grid;
    }

    public ArrayList<Character> getPentominoTypes() {
        return this.avaliableTypes;
    }

    public boolean removePentominoType(Character c){
        if (this.avaliableTypes.contains(c)){
            this.avaliableTypes.remove(avaliableTypes.indexOf(c));
            return true;
        } else {
            return false;
        }
    }

    public Coordinate getCoordinateToPlace(){

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (grid[i][j] == '.') return new Coordinate(i, j);
            }
        }
        return null;
    }

    public GridNode copy(){
        char[][] gridCopy = new char[this.grid.length][];
        for (int i = 0; i < grid.length; i++){
            gridCopy[i] = new char[grid[i].length];
            System.arraycopy(grid[i], 0, gridCopy[i], 0, grid[i].length);
        }

        return new GridNode(gridCopy, this.avaliableTypes);
    }

    public ArrayList<Pentomino> generateEdges() {
        ArrayList<Pentomino> allPermutations = new ArrayList<>();
        for (Character c : this.avaliableTypes) {
            for (boolean mirror : new boolean[]{false, true}) {
                for (int i = 0; i < 4; i++) {
                    Pentomino p = new Pentomino(c);
                    if (mirror) p.mirror();
                    for (int j = 0; j < i; j++) {
                        p.rotate();
                    }
                    allPermutations.add(p);
                }
            }
        }
        return allPermutations;
    }

    @Override
    public String toString(){
        String out = "";
        for (int gridLine = 0; gridLine < grid.length; gridLine++) {
            for (int j = 0; j < grid[gridLine].length; j++){
                out += grid[gridLine][j];
            }
            out += '\n';
        }
        return out;
    }

}
