package at.schaefer.david.Structure;

public class Item implements IIndex{
    public int[] index;

    public Item(int[] iIndex){
        index = iIndex;
    }

    public int[] GetIndex(){
        return index;
    }
}
