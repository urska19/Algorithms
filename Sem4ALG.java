import java.util.*;

public class Sem4ALG
{	
	//razred za hranjenje tock
	static class Point
	{
		int x;
		int y;
		
		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	

	//poisce index tocke z najnizjo y koordinato
	public static Point najnizja_y(ArrayList<Point> p)
	{
		Point miny = p.get(0);
		int m = 0;
		for(int i = 1; i < p.size(); i++)
		{
			if((p.get(i).y < miny.y) || (p.get(i).y == miny.y && p.get(i).x < miny.x))
			{
				miny = p.get(i);
				m = i;
			}
		}
		return miny;
	}

	//točke so zasuk v levo, ce zasuk > 0, točke so zasuk v desno, ce zasuk < 0,  točke so kolinearne, ce zasuk = 0 
	public static double zasuk(Point p, Point q, Point i)
	{
		return ((q.x - p.x)*(i.y - p.y) - (q.y - p.y)*(i.x - p.x));
	}


	//preveri, ce so tocke kolinearne	
	public static boolean soKolinearne(ArrayList<Point> points) 
	{
		if(points.size() < 2)	return true;

        for(int i = 2; i < points.size(); i++) 
        {
            if(zasuk(points.get(0), points.get(1), points.get(i)) != 0) {
                return false;
            }
        }
        return true;
    }		
		
		
 
		
		
	//poisce konveksno ovojnico z metodo gift wrapping	
	public static ArrayList<Point> giftwrap(ArrayList<Point> points)
	{	
		Point pointOnHull = najnizja_y(points);
		ArrayList<Point> hull = new ArrayList<Point>();
		int i = 0;
		Point endpoint = null;
		
		do{
			hull.add(pointOnHull);
			endpoint = points.get(0);
			
			for(int j = 1; j < points.size(); j++)
			{
				double z = zasuk(pointOnHull, endpoint, points.get(j));
				if(endpoint.equals(pointOnHull) || (z < 0))
				{
					endpoint = points.get(j);
				}
				else if(z == 0)
				{ //update, ce prva blizja in ni enaka sama sebi
					if(dist(pointOnHull, points.get(j)) > 0 && dist(pointOnHull, points.get(j)) < dist(endpoint, pointOnHull))
					{
						endpoint = points.get(j);
					}
				} 
			}
			pointOnHull = endpoint;
			
		}while(!endpoint.equals(hull.get(0)));
		
		return hull;
	}
			
	//vrne razdaljo med tockama		
	private static double dist(final Point p, final Point q)
    {
        final double dx = (q.x - p.x);
        final double dy = (q.y - p.y);
        return (dx * dx) + (dy * dy);
    }
 		
 	//vrne stevilo slojev	
 	public static int solve(int[] xa, int[] ya)
	{
		ArrayList<Point> points = new ArrayList<Point>();
		Point p;
		int stev = 0;
		int k;
		
		ArrayList<Point> hull = new ArrayList<Point>();

		if(xa.length == 0) return 0;
		if(xa.length < 4) return 1;
		else{
			for(int i = 0; i < xa.length; i++)
			{
				p = new Point(xa[i],ya[i]);
				points.add(p);
			}
			Collections.sort(points, new PointCompare());
//for(int u = 0; u < points.size(); u++)
//{//	System.out.println(points.get(u).x + " " + points.get(u).y);
//}
					

			
			if(soKolinearne(points)) return 1;
			

	
			
			while(true)
			{
				hull = giftwrap(points);

				for(int i = 0; i < hull.size(); i++)
				{
					points.remove(hull.get(i));
				}
				hull.clear();
				k = points.size();
				stev ++;
				if(k <= 3 && k > 0)
				{
					stev++;
					break;
				}
				if(k == 0) break;
			}
			
		}


	 return stev;		
	}			

	public static String studentId()
	{
		return "27122003";
	}


    static class PointCompare
        implements Comparator<Point> {

        public  int compare(final Point a, final Point b) {
            if (a.x < b.x) {
                return -1;
            }
            else if (a.x > b.x) {
                return 1;
            }
            else {
				if(a.y < b.y) return -1;
				else if (a.y > b.y) return 1;
                 else return 0;
            }
        }
       } 

	public static void main(String[] args) 
	{
		int[] x = {3, 5, 7, 1, 2, 8, 6};
		int[] y = {3, 4, 5, 1, 9, 6, 4};
		
		//solve(x,y);
		
	//System.out.println(points.get(u).x + " " + points.get(u).y);

		
		
	}



}
