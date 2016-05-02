package algorithms;

public class cs560project {
	public static final int N = 3;
	public static int[][][] mapTable;
	public static int [] xInvTable;
	public static int [] yInvTable;
	public static int [] zInvTable;
	
	
	

	
	public static void main(String[] args) {
	mapTable = new int[N][N][N];
	xInvTable = new int [N*N*N];
	yInvTable = new int [N*N*N];
	zInvTable = new int [N*N*N];
	
	int count = 0;
	for (int ix = 0; ix < N ; ix++) 
		for (int iy = 0 ; iy < N; iy++)
			for (int iz = 0; iz < N; iz++){
				mapTable[ix][iy][iz] = 1 << count;
				xInvTable[count] = ix;
				yInvTable[count] = iy;
				zInvTable[count] = iz;
				count++;
			}
	piece3D p = new piece3D ('A', 4);
	p.setCube(1,1,0,0);
	p.setCube(2,1,0,1);
	p.setCube(3,2,0,1);
	
	p.findAllPossiblePositionsAsBitmaps();
	
	}
}