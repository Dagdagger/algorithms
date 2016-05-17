package algorithms;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class cs560project
{
    public static int N;
    public static  int P;
    
    public static int pieceNumber;
    public static int cubeNumber;
    
    
    
    

    public static int[][][] mapTable ;
    public static int[] xInvTable ;
    public static int[] yInvTable ;
    public static int[] zInvTable ;
    
    public static piece3D[] pieces ;
    
    public static int[] solution ;
    public static boolean continuing ;
    
    public static char[][][] solutionCharMatrix ;
    
    
    //=====================================================================================================
    
    
    public static void outputSolution( ) 
    {
      //--- fill 3x3x3 char matrix with piece ID's per solution piece placements decoded from solution bitmaps
      
        for ( int whichPiece = 0 ; whichPiece < P ; whichPiece++ )
          {
            char idChar = pieces[whichPiece].pieceID ;
            int integratedBitmap = solution[whichPiece] ;
            for ( int i = 0 ; i < N*N*N ; i++ ) 
              {
                if ( integratedBitmap % 2 == 1 ) 
                  solutionCharMatrix[xInvTable[i]][yInvTable[i]][zInvTable[i]] = idChar ; 
                integratedBitmap >>= 1 ;                          
              }
          }

      //--- output 3x3x3 char matrix showing pieceID's at each location, layer-by-layer in z- direction    
          
        System.out.println( "HERE IS THE SOLUTION TO THE PROBLEM INSTANCE DISTRIBUTED IN CLASS" ) ;
        System.out.println( "AS SOLVED BY THE PROGRAM WHOSE SOURCE CODE IS GIVEN AS HINT #2" ) ;
        System.out.println( ) ;
        System.out.println( "EACH CELL OF THE 3x3x3 SOLUTION CUBE IS LABELED WITH THE CHAR PIECE-ID OF THE PIECE" ) ;
        System.out.println( "TO WHICH THE UNIT CUBE OCCUPYING THAT CELL BELONGS (OUTPUT LAYER-BY-LAYER IN Z-ORDER)" ) ;
        System.out.println( ) ;
        
        for ( int z = 2 ; z >= 0 ; z-- ) 
          {
            System.out.println( ) ;
            System.out.println( "z = " + z + " layer" ) ;
            System.out.println( ) ;
            for ( int x = 2 ; x >= 0 ; x-- ) 
              {
                for ( int y = 2 ; y >= 0 ; y-- ) 
                  System.out.print( solutionCharMatrix[x][y][z] ) ;
                System.out.println( ) ;
              }            
          }      

    }

    
    //=====================================================================================================
    
   
    public static void DFS ( int level , int priorPartialSolution ) 
    {

    	if ( level == P ) 
        {
           outputSolution( ) ;       
       		System.exit(0);
        }
    	else {
    		for (Integer ptr : pieces[level].possiblePositionsBitmaps.values() ){
    			int overlap = priorPartialSolution & ptr ;
                if ( overlap == 0 ) // no overlap
                  {
                    solution[level] = ptr ;
                    DFS ( level+1 , priorPartialSolution | ptr ) ;
                  }
    		}
    	}
    	
    	
      
    }
    
    
    /* bitmap code Breadth First Search
    
    public static void BFS ( int level , int priorPartialSolution ) 
    {
      //--- recursive breadth-first search to search for solution among all possible piece placement combinations 
        
        if ( level == P ) 
            {
               outputSolution( ) ;
               continuing = false;    
            }
          else
            {
               bitmapNode ptr = pieces[level].possiblePositionsBitmaps ;
              while ( (ptr != null) && continuing )
                {
                  int overlap = priorPartialSolution & ptr.bitmap ;
                  if ( overlap == 0 ) // no overlap
                    {
                      solution[level] = ptr.bitmap ;
                      BFS ( level+1 , priorPartialSolution | ptr.bitmap ) ;
                    }
                  ptr = ptr.link ;
                }
            }
    }
    
    */
    //=====================================================================================================
    
    
    public static void main(String[] args ) throws IOException 
    {
    	int counterN = 0;
    	char pieceName = 'A';
    	char charcounter = 'A';
    	FileInputStream finput = new FileInputStream("src/text2.txt");
    	BufferedReader buffRead = new BufferedReader(new InputStreamReader(finput));
    	
    	String line_counter = null;
    	line_counter = buffRead.readLine(); //Scanner
    	N = Integer.parseInt(line_counter);  //new N
    	line_counter = buffRead.readLine();
    	P = Integer.parseInt(line_counter);
    	
    	pieces = new piece3D[P];    //any pieces from line 2 of textfile
    	solution = new int[P] ;
        solutionCharMatrix = new char[N][N][N] ;  

        mapTable  = new int[N][N][N] ;
        xInvTable = new int[N*N*N] ;
        yInvTable = new int[N*N*N] ;
        zInvTable = new int[N*N*N] ;
        int count = 0 ;
        for ( int ix = 0 ; ix < N ; ix++ ) 
          for ( int iy = 0 ; iy < N ; iy++ ) 
            for ( int iz = 0 ; iz < N ; iz++ )
              {
                mapTable[ix][iy][iz] = 1 << count ;
                xInvTable[count] = ix ;
                yInvTable[count] = iy ;
                zInvTable[count] = iz ;
                count++ ;
              }
        
        for (int i = 0; i < P; i++) {    
        	line_counter = buffRead.readLine();
        	pieceNumber = Integer.parseInt(line_counter);
        	line_counter = buffRead.readLine();
        	cubeNumber = Integer.parseInt(line_counter);
        	
        	
     	pieces[pieceNumber - 1] = new piece3D(pieceName, cubeNumber);
     	pieceName++;
        	for (int x = 0; x < cubeNumber; x++) {
        		String str = buffRead.readLine();
        		// 3x3
        		int arr []  = new int [N];
        		for (int y = 0; y < N*2; y+=2) {	
        			char c =  str.charAt(y);
        			int k = (int)c - (int)'0';
        			if (counterN == N){
        				counterN=0;
        			}
        			arr[counterN] = k;
        			counterN++; 

        		}
        		
        		pieces[pieceNumber-1].setCube(x, arr[0], arr[1], arr[2]);
        	}
        }

        for ( int whichPiece = 0 ; whichPiece < P ; whichPiece++ ){ 
          pieces[whichPiece].findAllPossiblePositionsAsBitmaps( ) ;
        }
        
        /* Bitmap code
        continuing = true;
        */
        
        DFS ( 0 , 0 ) ;
      
    }

}
// ------------------------------
//----------------------------