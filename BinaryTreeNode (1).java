//Sophia Prochnow and Annie Flora
package tree.binary;

  public class BinaryTreeNode {

      private String data;
      private BinaryTreeNode left, right;

      BinaryTreeNode (String s) {
          data = s;
          left = null;
          right = null;
      }
      //adds data to tree
      public void add (String s, String child) {
          if (child.equals("L")) {
              left = new BinaryTreeNode(s);
          } else if (child.equals("R")) {
              right = new BinaryTreeNode(s);
          } else {
              throw new IllegalArgumentException();
          }
      }
      //returns child of current node
      public BinaryTreeNode getChild (String child) {
          return (child.equals("L")) ? left : right;
      }

      public String getString () {
          return data;
      }
      //returns original tree doubled
      public void doubleTree () {
          if (left == null) {
              add(data, "L");
          } else {
              BinaryTreeNode tempStore = left;
              this.add(data, "L");
              left.left = tempStore;
              left.left.doubleTree();
          }
          if (right != null) {
              right.doubleTree();
          }
      }
      //determines if 2 trees are identical
      public static boolean sameTree (BinaryTreeNode n1, BinaryTreeNode n2) {
          if (n1 == null && n2 == null) {
              return true;
          }
          if (n1 == null || n2 == null || n1.getString() != n2.getString()) {
              return false;
          }
          if (sameTree(n1.left, n2.left)) {
              if (sameTree(n1.right, n2.right)) {
                  return true;
              }
          }
          return false;
      }

  }
