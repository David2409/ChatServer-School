package at.schaefer.david.Structure;

public class Node {
    protected static final int CHILDRENCOUNT = Short.MAX_VALUE*2+1;
    protected Object[] children;
    protected Node parent;
    protected int pos;
    protected int count;
    protected Object countLock = new Object();
    protected Object addLock = new Object();
    protected int layer;

    protected Node(Node iParent, int iPos, int iLayer){
        parent = iParent;
        pos = iPos;
        layer = iLayer;
        count = 0;

        if(layer == 0){
            children = new IIndex[CHILDRENCOUNT];
        }
        else{
            children = new Node[CHILDRENCOUNT];
        }
    }

    public Node() { }

    public IIndex Add(IIndex item){
        int[] index = item.GetIndex();
        Node current = this;
        for(int by = 0; by < index.length-1; by++){
            if (current.children[index[by]] == null) {
                synchronized (current.countLock) {
                    current.count++;
                }
                synchronized (current.addLock){
                    if (current.children[index[by]] == null) {
                        current.children[index[by]] = new Node(this, index[by], current.layer-1);
                    }
                }
            }
            current = (Node) current.children[index[by]];
        }
        if (current.children[index[index.length-1]] == null) {
            synchronized (current.countLock) {
                current.count++;
            }
            synchronized (current.addLock) {
                if (current.children[index[index.length-1]] == null) {
                    current.children[index[index.length-1]] = item;
                }
            }
        }
        return (IIndex) current.children[index[index.length-1]];
    }

    public void Remove(IIndex item){
        int[] index = item.GetIndex();
        Node current = this;
        for(int by = 0; by < index.length-1; by++){
            if(current.children[index[by]] == null) {
                return;
            }
            current = (Node) current.children[index[by]];
        }
        synchronized (current.children[index[index.length-1]]){
            current.children[index[index.length-1]] = null;
        }
        try{
            synchronized (current.countLock){
                current.count--;
                while(current.count == 0){
                    current.count = -1;
                    synchronized (current.parent.countLock){
                        current.parent.count--;
                        synchronized (current.parent.children[pos]) {
                            current.parent.children[pos] = null;
                        }
                    }
                    current = current.parent;
                }
            }
        }
        catch (NullPointerException e) {}
    }

    protected void Delete(){

    }
}