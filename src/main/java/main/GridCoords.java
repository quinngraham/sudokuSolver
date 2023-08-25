package main;

public class GridCoords{


    public int rowVal;
    public int colVal;
    public GridCoords(int row, int col){
        rowVal = row;
        colVal = col;
    }

    public String toString(){
        return "[" + rowVal + ", " + colVal + "]";
    }


    //Not being as strict as I should be with this, basically just getting it to work
    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;
        if(o.getClass() != this.getClass())
            return false;
        final GridCoords other = (GridCoords) o;

        return other.rowVal == this.rowVal && other.colVal == this.colVal;
    }

    @Override
    public int hashCode(){
        int hash = 3;
        hash = 53 * hash + this.colVal;
        hash = 53 * hash + this.rowVal;
        return hash;
    }



}
