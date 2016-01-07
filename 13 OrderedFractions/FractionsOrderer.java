package OrderedFractions;

import java.util.*;
import java.io.*;

// http://acm.hit.edu.cn/hoj/problem/view?id=1076
// http://acmph.blogspot.ca/2013/01/usaco-ordered-fractions.html

public class FractionsOrderer {
	
	int maxDenom;
	boolean[][] visitedFractions = new boolean[256][256]; // default to false
	public TreeSet<Fraction> orderedFractions = new TreeSet<Fraction>();
//	public HashSet<Fraction> orderedFractions = new HashSet<Fraction>();
	List<Integer> input = new ArrayList<Integer>();
	
	static class Fraction implements Comparable<Fraction>{
		public int nom, denom;
		
		public Fraction(int nom, int denom){
			int divisor = greatestCommonDivisor(nom, denom);
			this.nom = nom/divisor;
			this.denom = denom/divisor;
		}
		
		private int greatestCommonDivisor(int a, int b) {
			if (b==0) return a;
			return greatestCommonDivisor(b,a%b);
		}
		
		public String toString(){
			return String.format("%1d/%1d", nom, denom);
		}
		
		public boolean equals(Object object){
			Fraction other = (Fraction) object;
			return this.nom == other.nom && this.denom == other.denom;
		}

		public int compareTo(Fraction other) {
			if(this.nom == other.nom && this.denom == other.denom) return 0;
			return (this.nom*other.denom < this.denom*other.nom) ? -1 : 1;
		}
		
		public int hashCode(){
			return this.nom*this.denom;
		}
	}
	
	public void depthFirstSearch(int nom, int denom){
		if(denom>maxDenom || nom>denom) return;
		if(visitedFractions[nom][denom]==true)return;
		
		visitedFractions[nom][denom]=true;
		orderedFractions.add(new Fraction(nom, denom));
		
		depthFirstSearch(nom+1, denom);
		depthFirstSearch(nom, denom+1);
		depthFirstSearch(nom+1, denom+1);
	}
	
	public void setMaxDenom(int x){
		maxDenom = x;
	}
	
	public void clearData(){
		for(int i=0;i<256;i++)
			for(int j=0;j<256;j++)
				visitedFractions[i][j]=false;
		
		orderedFractions.clear();
	}
	
	private void readFile(String file){
		//use . to get current directory
		try{
			File dir = new File(".");
			File finput = new File(dir.getCanonicalPath() + File.separator + "OrderedFractions" + File.separator + file);
			FileInputStream finputstream = new FileInputStream(finput);
		 
			//Construct BufferedReader from InputStreamReader
			BufferedReader br = new BufferedReader(new InputStreamReader(finputstream));
		 
			String line = null;
			while ((line = br.readLine()) != null) {
				this.input.add(Integer.parseInt(line));
			}
			br.close();
		} catch (IOException ex)	{
			System.out.println("Input file not found");
		}
	}
	
	private void writeOutput(String file){
		//use . to get current directory
		try{
			File dir = new File(".");
			File fout = new File(dir.getCanonicalPath() + File.separator + "OrderedFractions" + File.separator + file);
		 
			BufferedWriter bw = new BufferedWriter(new FileWriter(fout, true));
			bw.write(orderedFractions.toString()+"\n");
			bw.close();
		} catch (IOException ex){
			System.out.println("Output file not found");
		}
	}

	public static void main(String[] args) {
		FractionsOrderer orderer = new FractionsOrderer();
		orderer.readFile("input.txt");
		Iterator<Integer> iterator = orderer.input.iterator();
		while(iterator.hasNext()){
		    orderer.setMaxDenom(iterator.next());
			orderer.clearData();
			orderer.depthFirstSearch(0,1);
			orderer.writeOutput("output.txt");
		}
	}

}
