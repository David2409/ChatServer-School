package at.schaefer.david.Structure;

import at.schaefer.david.General.Server;

import java.sql.SQLException;

public class Tree extends Node {
    public Tree(int depth){
        super(null, 0, depth-1);
    }

    public static void main(String[] args) throws SQLException {
        Tree t = new Tree(8);
        t.Add(Server.GetServer(100l));
    }

    public static void PrintArray(int[] array){
        for(int i = 0; i < array.length; i++){
            System.out.println(array[i]);
        }
    }
}