import java.util.*;

public class Sem3ALG
{	
//pomozna...prepise slovnico v BNF obliko, ce je potrebno
	public static String[] toBNF(String[] grammar)
	{
		String[] tabela = {"Z","Y","X","W","V","U","T","R","Q","P","O","N","M","L","K", "J", "I"}; //za nove produkcije
		ArrayList<String> G = new ArrayList<String>();
		ArrayList<String> brisimo = new ArrayList<String>();
		int k = 0;
		String tmp = "";
		int flag = 0;
			
		for(int i = 0; i < grammar.length; i++)
		{
			G.add(grammar[i]);
		}	
//epsilon produkcije
		for(int i = 0; i < G.size(); i++)
		{
			String eps = "";
			if(G.get(i).charAt(3) == '~' && G.get(i).charAt(0) != 'S')
			{				
				eps = "" + G.get(i).charAt(0);
				String beseda = "";
				for(int j = 0; j < G.size(); j++)
				{
					if(G.get(j).length() == 4)
					{
						if(G.get(j).charAt(3) == eps.charAt(0))
						{
							if(G.contains(G.get(j).substring(0,3) + '~') == false)
							{
								G.add(G.get(j).substring(0,3) + '~');
							}	
						}
					}	
					
					else if(G.get(j).length() > 4)
					{
						beseda = G.get(j);
					
						for(int l = 3; l < beseda.length(); l++)
						{
							if(beseda.charAt(l) == eps.charAt(0))
							{
								if(G.contains(beseda.substring(0,l) +  beseda.substring(l+1,beseda.length())) == false)
								{
									String b = beseda.substring(0,l)+  beseda.substring(l+1,beseda.length());
									G.add(b);	
								}
							}
							
						}
					}
					
					
				}	
				brisimo.add(G.get(i));		
			}
		}

		for(int i = 0; i < brisimo.size(); i++)
		{
			G.remove(brisimo.get(i));
		}
		
		ArrayList<String> zbrisi = new ArrayList<String>();
		ArrayList<String> nove = new ArrayList<String>();
//zapise po 2 simbola na desni strani vsake produkcije, kjer je to potrebno
		for(int i = 0; i < G.size(); i++)
		{
			if(G.get(i).length() > 6)
			{
				String S = "";
				if((G.get(i).length() % 2) == 1)
				{
					for(int j = 3; j < G.get(i).length(); j+=2)
					{
						String dva = G.get(i).charAt(j) + "" + G.get(i).charAt(j+1);
						if(nove.size() == 0) 
						{
							G.add(tabela[k] + "->" + dva);
							nove.add(tabela[k] + "->" + dva);
							S = S + tabela[k];
							k++;
							if(k >= tabela.length) break;
						}
						else
						{
							for(int m = 0; m < nove.size(); m++)
							{
								if((nove.get(m).substring(nove.get(m).indexOf(">") + 1, nove.get(m).length())).equals(dva) == true)
								{
									S = S + nove.get(m).charAt(0);
									flag = 1;
									break;
								}
							}	
							if(flag == 0)
							{
								G.add(tabela[k] + "->" + dva);
								nove.add(tabela[k] + "->" + dva);
								S = S + tabela[k];
								k++;
								if(k >= tabela.length) break;
							}
							flag = 0;	
						}
					}   
					G.add(G.get(i).substring(0,3) + S);
				}
								
				if((G.get(i).length() % 2) == 0)
				{
					for(int j = 3; j < G.get(i).length()-1; j+=2)
					{
						String dva = G.get(i).charAt(j) + "" + G.get(i).charAt(j+1);
						if(nove.size() == 0)
						{
							G.add(tabela[k] + "->" + dva);
							nove.add(tabela[k] + "->" + dva);
							S = S + tabela[k];
							k++;
							if(k >= tabela.length) break;
						}	
						else
						{
						for(int l = 0; l < nove.size(); l++)
						{
							if((nove.get(l).substring(nove.get(l).indexOf(">") + 1, nove.get(l).length())).equals(dva) == true)
							{
								S = S + nove.get(l).charAt(0);
								flag = 1;
								break;
							}
						}	
						
						if(flag == 0) 
						{
							G.add(tabela[k] + "->" + dva);
							nove.add(tabela[k] + "->" + dva);
							S = S + tabela[k];
							k++;
							if(k >= tabela.length) break;
						}	
						flag = 0;
	
						}
					}  
					G.add(G.get(i).substring(0,3) + S + G.get(i).charAt(G.get(i).length()-1)); 
				}
				zbrisi.add(G.get(i));	
			}
		}
		
		for(int i = 0; i < zbrisi.size(); i++)
		{
				G.remove(zbrisi.get(i));
		}

		zbrisi.clear();
		flag = 0;
		
		for(int i = 0; i < G.size(); i++)
		{
			if(G.get(i).length() == 6)
			{
				String dva1 = G.get(i).charAt(3) + "" + G.get(i).charAt(4);
				String dva2 = G.get(i).charAt(4) + "" + G.get(i).charAt(5);
				//ce prve dva se ujemata ali druge dva
				for(int j = 0; j < nove.size(); j++)
				{
					if(((nove.get(j).charAt(3) + "" + nove.get(j).charAt(4)).equals(dva1) == true) ||
						((nove.get(j).charAt(3) + "" + nove.get(j).charAt(4)).equals(dva2) == true ))
					{
						flag = 1;
						if((nove.get(j).charAt(3) + "" + nove.get(j).charAt(4)).equals(dva1) == true)
						{
							if(G.contains(G.get(i).substring(0,3) + nove.get(j).charAt(0) + G.get(i).charAt(5)) == false)
							{
								G.add(G.get(i).substring(0,3) + nove.get(j).charAt(0) + G.get(i).charAt(5));			
							}	
							zbrisi.add(G.get(i));
						}
						else
						{
							if(G.contains(G.get(i).substring(0,4) + nove.get(j).charAt(0)) == false)
							{
								G.add(G.get(i).substring(0,4) + nove.get(j).charAt(0));
								
							}
							zbrisi.add(G.get(i));							
						}
						break;
					}
				}	
				if(flag == 0)
				{
					if(G.contains(G.get(i).substring(0,3) + tabela[k] + G.get(i).charAt(5)) == false)
					{
						G.add(G.get(i).substring(0,3) + tabela[k] + G.get(i).charAt(5));
					}
					zbrisi.add(G.get(i));
					nove.add(tabela[k] + "->" + G.get(i).charAt(3) + G.get(i).charAt(4));
					k++;
					if(k >= tabela.length) break;
				}
				flag = 0;
		    }		
		}		
				
		for(int i = 0; i < zbrisi.size(); i++)
		{
				G.remove(zbrisi.get(i));
		}
			
		//koncne terminale na desni strani produkcije, ki nastopajo 
		//z nekoncnim, spremeni v nekonce in doda novo produkcijo za
		//koncni simbol, ce je potrebno
		zbrisi.clear();
		ArrayList<String> enotne = new ArrayList<String>();
		for(int i = 0; i < G.size(); i++)
		{
			if(G.get(i).length() == 4 && Character.isLowerCase(G.get(i).charAt(3)))
			{
				enotne.add(G.get(i));
			}
		}
		flag = 0;

		for(int i = 0; i < G.size(); i++)
		{
			if((G.get(i).length() == 5 && Character.isLowerCase(G.get(i).charAt(3))) ||	
				(G.get(i).length() == 5 && Character.isLowerCase(G.get(i).charAt(4))))
			{	
				for(int j = 3; j < 5; j++)
				{
					if(Character.isLowerCase(G.get(i).charAt(j)))
					{
						for(int m = 0; m < enotne.size(); m++)
						{
							if(enotne.get(m).charAt(3) == G.get(i).charAt(j))
							{
								G.add(G.get(i).substring(0,j) + enotne.get(m).charAt(0) + G.get(i).substring(j+1,G.get(i).length()));
								flag = 1;
								break;
							}
						}	
						if (flag == 0)
						{
							G.add(G.get(i).substring(0,j) + tabela[k] + G.get(i).substring(j+1,G.get(i).length()));
							G.add(tabela[k] + "->" + G.get(i).charAt(j));
							enotne.add(tabela[k] + "->" + G.get(i).charAt(j));
							k++;
							if(k >= tabela.length)break;
						}
						flag = 0;
					}
						
				}
				zbrisi.add(G.get(i));	
			}	
		}

		for(int i = 0; i < zbrisi.size(); i++)
		{
				G.remove(zbrisi.get(i));
		}
//enotne produkcije z enim nekoncnim terminalom na desni strani prepise v ustrezne	
		zbrisi.clear();
		for(int i = 0; i < G.size(); i++)
		{
			if(G.get(i).length() == 4 && Character.isUpperCase(G.get(i).charAt(3)))
			{
				for(int j = 0; j < G.size(); j++)
				{
					if((G.get(i).charAt(3) == G.get(j).charAt(0) ))
					{
						if(G.contains(G.get(i).charAt(0)+ G.get(j).substring(1,G.get(j).length())) == false)
						{
							G.add(G.get(i).charAt(0)+ G.get(j).substring(1,G.get(j).length()));
						}	
						
					}	
				}
				zbrisi.add(G.get(i));
			}
		}
				
		for(int i = 0; i < zbrisi.size(); i++)
		{			
			G.remove(zbrisi.get(i));			
		}	
		
		String[] gr = new String[G.size()];
		for(int i = 0; i < G.size(); i++)
		{
			gr[i] = G.get(i);
		}
		return gr;		
	}

	
	
//pomozna...zapise vsako produkcijo zase (v primeru, ko produkcija vsebuje simbol "|")	
	public static String[] razcleni(String[] parts){

		ArrayList<String> pro = new ArrayList<String>();
		String tmp = "";
		for(int i = 0; i < parts.length; i++)
		{
			if(parts[i].contains("|"))
			{	
				tmp = parts[i];
				while(tmp.contains("|"))
				{
					pro.add(tmp.substring(0,tmp.indexOf("|")));
					tmp = tmp.substring(0,tmp.indexOf(">")+1)+ tmp.substring(tmp.indexOf("|")+ 1, tmp.length());
				}
				pro.add(tmp);
			}				
			else
			{
				pro.add(parts[i]);
			}
		}
		String[] G = new String[pro.size()];
		for(int i = 0; i < pro.size(); i++)
		{
			G[i] = pro.get(i);
		}
		return G;
	}
	
	//CYK algoritem
	public static boolean solve(String[] grammar_z, String word)
	{		
		String[] gr = razcleni(grammar_z);
		String[] grammar = toBNF(gr);
		 
		int n = word.length();
		int r = grammar.length;
		String unit_rule = "";
		String rule = "";
		int A;
		
		ArrayList<Integer> B = new ArrayList<Integer>();
		ArrayList<Integer> C = new ArrayList<Integer>();
		ArrayList<Integer> S = new ArrayList<Integer>();
		boolean P[][][] = new boolean[n][n][r];
		
		//inicializaija matrike P
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
			{
				for(int k = 0; k < r; k++)
				{
					P[i][j][k] = false;
					 
				}
			}
		}

		//unit productions
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < r; j++) 
			{
				unit_rule = grammar[j]; 
				if(unit_rule.length() == 4)
				{
					if(word.charAt(i) == unit_rule.charAt(3))
					{
						P[i][0][j] = true;
					}
					
				}
			}
		}
		
		//non-unit productions
		for(int i = 1; i < n; i++)
		{
			for(int j = 0; j < n-i; j++)
			{

				for(int k = 0; k < i; k++) 
				{
					for(int l = 0; l < r; l++)
					{	

						if(grammar[l].length() == 5)
						{

							rule = grammar[l];
							A = l;

							//returns ArrayList of idxs
							B = pos(grammar, rule.charAt(3));
							C = pos(grammar, rule.charAt(4));
												
							for(int u = 0; u < B.size(); u++)
							{
								for(int v = 0; v < C.size(); v++)
								{
									if(P[j][k][B.get(u)] == true && P[j+k+1][i-k-1][C.get(v)] == true)
									{

										P[j][i][A] = true;
									}
								}
							}			
						}	
					}
				}
				
			}
		}
		S = posS(grammar, 'S');
		for(int v = 0; v < S.size(); v++)
		{
			if(P[0][n-1][S.get(v)] == true)
			{
				return  true;
			}				
		}
		
		return false;
	}


//pomozna...poisce, katere produkcije se zacnejo z zacetnim simbolom S
public static ArrayList<Integer> posS(String[] grammar, char c)
	{
		ArrayList<Integer> L = new ArrayList<Integer>();

		for(int i = 0; i < grammar.length; i++)
		{
			if(c == grammar[i].charAt(0))
			{
				L.add(i);
			}
		}
		return L;
	}
	
//pomozna...poisce produkcije, ki se zacnejo s terminalom c
	public static ArrayList<Integer> pos(String[] grammar, char c)
	{
		ArrayList<Integer> L = new ArrayList<Integer>();

		for(int i = 0; i < grammar.length; i++)
		{
			if(c == grammar[i].charAt(0))
			{
				L.add(i);
			}
		}
		return L;
	}


	public static String studentId()
	{
		return "27122003";
	}

}
