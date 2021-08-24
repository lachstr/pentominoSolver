import java.util.HashMap;

/**
 * Pentomino class stores simple representations of all 12 unique pentominoes.
 * Takes a char paramater in constructor to dictate what pentomino to
 * represent. Offers methods for mirroring and rotating.
 * 
 * By Lachlan Macartney, Suppanut Chaimathikul
 * Toby Meyrick, Jake Perkins
 */
public class Pentomino {
    private final char type;
    private char[][] matrix = new char[5][5];
    /**
     * layoutMap represents the pentomino. It selects the appropriate one by
     * using the argument given in the constructor.
     */
    final static private HashMap<Character,char[][]> layoutMap = new HashMap<>();
    static {
        layoutMap.put('O',new char[][] {{'O', ' ', ' ', ' ', ' '},
                                        {'O', ' ', ' ', ' ', ' '},
                                        {'O', ' ', ' ', ' ', ' '},
                                        {'O', ' ', ' ', ' ', ' '},
                                        {'O', ' ', ' ', ' ', ' '}});

        layoutMap.put('P',new char[][] {{'P', 'P', ' ', ' ', ' '},
                                        {'P', 'P', ' ', ' ', ' '},
                                        {'P', ' ', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '}});

        layoutMap.put('Q',new char[][] {{'Q', 'Q', ' ', ' ', ' '},
                                        {' ', 'Q', ' ', ' ', ' '},
                                        {' ', 'Q', ' ', ' ', ' '},
                                        {' ', 'Q', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '}});

        layoutMap.put('R',new char[][] {{' ', 'R', 'R', ' ', ' '},
                                        {'R', 'R', ' ', ' ', ' '},
                                        {' ', 'R', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '}});

        layoutMap.put('S',new char[][] {{' ', ' ', 'S', 'S', ' '},
                                        {'S', 'S', 'S', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '}});

        layoutMap.put('T',new char[][] {{'T', 'T', 'T', ' ', ' '},
                                        {' ', 'T', ' ', ' ', ' '},
                                        {' ', 'T', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '}});

        layoutMap.put('U',new char[][] {{'U', ' ', 'U', ' ', ' '},
                                        {'U', 'U', 'U', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '}});

        layoutMap.put('V',new char[][] {{'V', ' ', ' ', ' ', ' '},
                                        {'V', ' ', ' ', ' ', ' '},
                                        {'V', 'V', 'V', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '}});

        layoutMap.put('W',new char[][] {{'W', ' ', ' ', ' ', ' '},
                                        {'W', 'W', ' ', ' ', ' '},
                                        {' ', 'W', 'W', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '}});

        layoutMap.put('X',new char[][] {{' ', 'X', ' ', ' ', ' '},
                                        {'X', 'X', 'X', ' ', ' '},
                                        {' ', 'X', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '}});

        layoutMap.put('Y',new char[][] {{' ', 'Y', ' ', ' ', ' '},
                                        {'Y', 'Y', ' ', ' ', ' '},
                                        {' ', 'Y', ' ', ' ', ' '},
                                        {' ', 'Y', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '}});

        layoutMap.put('Z',new char[][] {{'Z', 'Z', ' ', ' ', ' '},
                                        {' ', 'Z', ' ', ' ', ' '},
                                        {' ', 'Z', 'Z', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '},
                                        {' ', ' ', ' ', ' ', ' '}});
    }

    public char getType() {
        return type;
    }

    /**
     * Pentomino constructor uses the argument to select the appropriate
     * pentomino representation.
     * @param type - char representing the required pentomino
     */
    public Pentomino(char type){
        this.type = type;
        char[][] layout = layoutMap.get(type);

        if (layout == null){
            throw new IllegalArgumentException
                    ("Pentomino should be of type 'O', 'P', ... , 'Z'");
        }

        this.overwriteWith(layout);
    }

    public boolean canPlaceOn(GridNode node){
        return placeOnGrid(node, false);
    }

    public void placeOn(GridNode node){
        placeOnGrid(node, true);
    }

    private boolean placeOnGrid(GridNode node, boolean write){
        char[][] grid = node.grid;
        Coordinate c = node.getCoordinateToPlace();

        int height = grid.length;
        int width = grid[0].length;

        assert c.x < width && c.y < height;
        Coordinate start = this.topLeft();
        assert start != null;
        int count = 0;
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){

                /*early return*/
                if (count == 5){
                    return true;
                }
                if (this.matrix[i][j] != ' '){
                    int xPrime = c.x+(i-start.x);
                    int yPrime = c.y+(j-start.y);
                    if ( xPrime < 0 || xPrime >= grid.length ||
                            yPrime < 0 || yPrime >= grid[0].length ||
                            grid[xPrime][yPrime] != '.'){
                        /*the pentomino goes off the board or hits another pentomino if placed here*/
                        return false;
                    } else {
                        if (write) grid[c.x+(i-start.x)][c.y+(j-start.y)] = this.matrix[i][j];
                        count++;
                    }
                }
            }
        }
        /*never gets here, but just appeasing java.*/
        return count == 5;
    }


    private Coordinate topLeft(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (this.matrix[i][j] != ' '){
                    return new Coordinate(i, j);
                }
            }
        }
        return null;
    }

    /**
     * toString overrides the default constructor to appear as the
     * pentomino looks in real life, not a code version.
     * @return - string to print
     */
    @Override
    public String toString(){
        String out = "";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                out += this.matrix[i][j];
            }
            out += "\n";
        }
        return out;
    }

    /**
     * overwriteWith takes the current representation of the
     * pentomino and overwrites it with the new representation
     * provided as argument by mirror or rotate method. Crucial
     * last step in completing a successful mirror or rotation.
     * @param source - new representation of the pentomino
     */
    private void overwriteWith(char[][] source){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.matrix[i][j] = source[i][j];
            }
        }
    }

    /** 
     * mirror method mirrors pentominos across a diagonal axis.
     * Support method used by main method to mirror the pentomino
     * representation. Accesses overwriteWith to complete the
     * mirror.
     */
    public void mirror() {
        char[][] mirror = new char[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mirror[j][i] = matrix[i][j];
            }
        }

        this.overwriteWith(mirror);
    }

    /**
     * rotate rotates pentominos 90 degrees clockwise. Support
     * method used by main method to rotate the pentomino
     * representation. Accesses overwriteWith to complete the
     * mirror.
     */
    public void rotate() {
        char[][] rotated = new char[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                rotated[j][4-i] = this.matrix[i][j];
            }
        }

        this.overwriteWith(rotated);
    }

}
