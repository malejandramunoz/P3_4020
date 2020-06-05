package strategies;

import java.util.ArrayList;
import java.util.List;


/**
 * Binary tree printer
 * https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
 * 
 * @author MightyPork
 */
public class BinaryTreePrinter
{
    /** Node that can be printed */
    public interface PrintableNode
    {
        /** Get left child */
        PrintableNode getLeft();

        /** Get right child */
        PrintableNode getRight();

        /** Get text to be printed */
        String getText();
    }
}