
import java.io.*;
//for bits used provided BitInputStream and BitOutputStream

//readers/writers are for reading/writing characters

//parent of all checked I/O exceptions

//pretty much all the data structures
import java.util.*;


//the class with the main method
class Huffman {
	final int MAGICNUMBER = 0;
	final int MAGICSIZE = 32;
	static int TreeHieght;
	static String decodedString = "";
	static List<Integer> list;
	static BinaryTree Tree;
	static String TreeRoadMap;

	public static void main(String[] args) throws IOException {
		if(args.length != 1) System.out.println("Usage: java Huffman <inputFile>");
		String filename = args[0];


		File file = new File(args[0]);
		InputStream input = new FileInputStream(file);

		BitInputStream BitInput = new BitInputStream(input);
		BitInput.startBitMode();

		Tree = new BinaryTree();

		if(!magicNumberCheck(BitInput))
		{
			System.out.println("File " + file.getName() + " is not a valid binary file.");
		}
		else{
			setTreeHieght(BitInput);
			System.out.println("Given Tree Hieght "+getTreeHieght());
			list = new ArrayList<Integer>();
			addToList(BitInput);
			Tree.setRoot(Tree.addTreeNode(list,0,maxNumOfNodes()));
		}

		for(int i = 0; i < list.size(); i++)
		{
			System.out.print(list.get(i) + " ");
		}



		TreeRoadMap = getHuffmanTreeRoadMap();
		System.out.println("\nRoad Map: " + TreeRoadMap);
		System.out.println("Max depth "+maxDepth(Tree.root));
		System.out.println("Max number of nodes "+maxNumOfNodes());
		System.out.println("List size "+list.size());
		decode(Tree.root,0);

		System.out.println("Decoded String: " + decodedString);

		FileWriter  newfile = new FileWriter(filename + ".txt");
		newfile.write(decodedString);
		newfile.close();
		//decode(list, Tree.root,list.get(list.size()-1));
		//System.out.println("Decoded: " + decodedString);

	}

	public static String getHuffmanTreeRoadMap(){
		String revBinary="";
		for(int j = maxNumOfNodes(); j < list.size(); j++){
			String binary = Integer.toBinaryString(list.get(j));
			char ch;
			for (int i=0; i<binary.length(); i++)
			{
				ch= binary.charAt(i);
				revBinary= ch+revBinary;
			}
			/*while(revBinary.length()%8!=0){
				revBinary = revBinary+'0';
			}*/
		}
		return revBinary;
	}
	public static void decode(Node node, int num){
		if(node.getValue()==3){
			decodedString += "";
		}
		else if(node.getValue()==1){
				if(TreeRoadMap.charAt(num)=='1'){
					decode(node.getRight(),num+1);
				}
				else if(TreeRoadMap.charAt(num)=='0'){
					decode(node.getLeft(),num+1);
				}
		}
		else if((node.getRight()==null && node.getLeft()==null) || (node.getRight().getValue()==0 && node.getLeft().getValue()==0)) {
			decodedString += Character.toString((char) node.getValue());
			decode(Tree.root,num);
		}
		//return ret + String.valueOf(node.getValue());
	}
	public static void addToList(BitInputStream BitInput){
		while(BitInput.hasNext()){
			list.add(getNextEight(BitInput));
		}
	}
	public static boolean magicNumberCheck(BitInputStream BitInput)
	{
		int CheckNumber = 0;
		//BitInput.startBitMode();
		int c = 0;
		while(BitInput.hasNext()){
			CheckNumber += getNextEight(BitInput);
			c++;
			if(c == 4)
				break;
		}
		return (CheckNumber == 0);
	}

	public static int getNextEight(BitInputStream BitInput){
		int ret = 0;
		int BinMulti = 1;
		int c = 0;
		while(BitInput.hasNext()){
			ret += BinMulti*BitInput.readNext();
			BinMulti *= 2;
			c++;
			if(c == 8)
				break;
		}
		return ret;
	}

	public static void setTreeHieght(BitInputStream BitInput){
		TreeHieght = getNextEight(BitInput);
	}
	public static int getTreeHieght(){
		return TreeHieght;
	}

	public static int maxNumOfNodes()
	{
		int ret = 0;
		for(int i = 0; i <= getTreeHieght(); i++){
			double m = Math.pow(2,i);
			ret = ret + (int)m;
		}
		return ret;
	}
	public static int maxDepth(Node node)
	{
		if (node == null)
			return 0;
		else {
			/* compute the depth of each subtree */
			int lDepth = maxDepth(node.left);
			int rDepth = maxDepth(node.right);

			/* use the larger one */
			if (lDepth > rDepth)
				return (lDepth + 1);
			else
				return (rDepth + 1);
		}
	}
}



