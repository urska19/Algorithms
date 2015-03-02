import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


class MyCallable implements Callable<String> 
{
	private ArrayList<String> besede;
	private String b;

	public MyCallable(ArrayList<String> seznam, String beseda)
	{	
		besede = seznam;
		b = beseda;
	}

	@Override
	public String call() throws Exception
	{
		boolean imam = false;
		int i = 0;
		while (i < besede.size() && !imam) 
		{
			for(int j = 0; j < ((besede.get(i).length()+1) - b.length()); j++) 
			{
				if(besede.get(i).charAt(j) == b.charAt(0)) 
				{
					imam = true;
					for(int k = 1; (k < b.length()) && imam; k++) 
					{
						imam = (imam && (b.charAt(k) == besede.get(i).charAt(j+k)));
					}
				}
			}
			i++;
		}
		if(imam) return b;
		else return null;	
	}
}


public class Sem5ALG 
{

	public static String studentId()
	{
		return "27122003";
	}

//besede iz vseh moznih smeri prepise v seznam
	public static ArrayList<String> naredi(char[][] grid)
	{
			int n = grid.length;
			String s1 = ""; String s2 = ""; String s3 = ""; String s4 = "";
			ArrayList<String> tab = new ArrayList<String>();			

			for (int x = 0; x < n; x++)
			{
				s1 = ""; s2 = ""; s3 = ""; s4 = "";
				for (int y = 0; y < n; y++)
				{
					s1 = s1 + grid[x][y]; //levo - desno
					s2 = s2 + grid[x][n-1-y];//desno - levo
					s3 = s3 + grid[y][x];//gor - dol
					s4 = s4 + grid[n-1-y][x];//dol - gor
				}
				tab.add(s1); tab.add(s2); 
				tab.add(s3); tab.add(s4);
			}
						
			for (int i = 0; i < 2 * n - 1; i++) 
			{
				s1 = ""; s2 = ""; s3 = ""; s4 = "";
				int z = i < n ? 0 : i - n + 1;
				for (int j = z; j <= i - z; j++) 
				{
					s1 = s1 + grid[j][i-j];//d1
					s2 = s2 + grid[i-j][j];//d2
					s3 = s3 + grid[j][(n-1)-(i-j)];//d3
					s4 = s4 + grid[(i-j)][(n-1)-j];//d4
				}
				tab.add(s1); tab.add(s2); 
				tab.add(s3); tab.add(s4);
		}
						
		return tab;
	}
//isce besede, ce je ena nit dovoljena	
	public static ArrayList<String> poisci_besede(ArrayList<String> besede, String[] words)
	{
		ArrayList<String> najdene = new ArrayList<String>();
			
		for(int i = 0; i < words.length; i++)
		{
			if(isci(words[i], besede)) najdene.add(words[i]);
		}
		return najdene;
	}	
//isce besede, ce je ena nit dovoljena				
	public static boolean isci(String b, ArrayList<String> seznam) 
	{
		boolean imam = false;
		int i = 0;
		while (i < seznam.size() && !imam) 
		{
			for(int j = 0; j < ((seznam.get(i).length()+1) - b.length()); j++) 
			{
				if(seznam.get(i).charAt(j) == b.charAt(0)) 
				{
					imam = true;
					for(int k = 1; (k < b.length()) && imam; k++) 
					{
						imam = (imam && (b.charAt(k) == seznam.get(i).charAt(j+k)));
					}
				}
			}
			i++;
		}
		return imam;
}
		
		
	public static String[] solve(String[] words, char[][] grid, int maxThreads)
	{
		int st_niti = maxThreads-1;
		int st_nalog = words.length;		
		ArrayList<String> seznam = naredi(grid);
		ArrayList<String> iz = new ArrayList<String>();
		
		if(st_niti == 0) 
		{
			iz = poisci_besede(seznam, words);
		} 
		else
		{
			ExecutorService executor = Executors.newFixedThreadPool(st_niti);
			List<Future<String>> list = new ArrayList<Future<String>>();
		
			for(int i = 0; i < st_nalog; i++)
			{
				Future<String> future = executor.submit(new MyCallable(seznam, words[i]));
				list.add(future);
			}

			
			for(Future<String> fut : list)
			{
				try
				{
					String temp = fut.get(); 
					if(temp!= null) iz.add(temp);
				} catch (Exception e)  
				{
					e.printStackTrace();
				}	
			}
			executor.shutdown();
		}	
			//vrni najdene
			String[] izhod = new String[iz.size()];

			for(int i = 0; i < iz.size(); i++)
			{
				izhod[i] = iz.get(i);
				System.out.print(iz.get(i) + " ");
			}
			System.out.println();
			
			return izhod;
		}
	}
