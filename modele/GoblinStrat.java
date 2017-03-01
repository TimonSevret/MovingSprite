package modele;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class GoblinStrat extends Strategie {
	
	private List<Eval> actions = new ArrayList<>();
	private int numeroAction = 0;
	
	
	/*
	 * utiliser dans GoblinStrat(BufferedReader fichier)
	 * ces deux class interne devaient etre créés en temps que class anonnyme comme pour attendre
	 * seulement la necessiter de posseder un argument l'a empecher
	 */
	private class Mouvement implements Eval{
		
		int mouvement;
		
		public Mouvement(int mouvement){
			this.mouvement = mouvement;
		}
		
		@Override
		public int eval(Entite e,Entite [][] tiles, List<Entite> l, List<EntiteTrace> lTrace){
			deplacement.x = mouvement;
			return Physique.move(e, tiles, l, lTrace);
		}

	}
	
	private class Saut implements Eval{
		
		int mouvement;
		
		public Saut(int mouvement){
			this.mouvement = mouvement;
		}
		
		@Override
		public int eval(Entite e,Entite [][] tiles, List<Entite> l, List<EntiteTrace> lTrace){
			if(!enVols)
				deplacement.y += mouvement;
			return Physique.move(e, tiles, l, lTrace);
		}

	}
	
	public GoblinStrat(BufferedReader fichier){
		super(new Point(0,0));
		StringTokenizer st;
		boolean continuer = true;
		try{
			while(continuer){
				String s = fichier.readLine();
				if(s != null){
					st = new StringTokenizer(s);
					s = st.nextToken();
					switch(s){
					case "deplacement:":
						try{
							if(st.hasMoreTokens())
								actions.add(new Mouvement(Integer.parseInt(st.nextToken())));
							else
								throw new NumberFormatException();
						}catch(NumberFormatException e){
							System.out.println("GoblinStrat :\n un deplacement possede une valeur erroné");
						}
						break;
					case "attendre:":
						int i = 0;
						try{
							if(st.hasMoreTokens())
								i = Integer.parseInt(st.nextToken());
							else
								throw new NumberFormatException();
						}catch(NumberFormatException e){
							System.out.println("GoblinStrat :\n une attente possede une valeur erroné");
						}
						while(i-- != 0)
							actions.add(
									new Eval(){
										
										@Override
										public int eval(Entite e,Entite [][] tiles, List<Entite> l, List<EntiteTrace> lTrace){
											deplacement.x = 0;
											return Physique.move(e, tiles, l, lTrace);
										}
									}
									);
						break;
					case "saut:" :
						try{
							if(st.hasMoreTokens())
								actions.add(new Saut(Integer.parseInt(st.nextToken())));
							else
								throw new NumberFormatException();
						}catch(NumberFormatException e){
							System.out.println("GoblinStrat :\n un saut possede une valeur erroné");
						}
						break;
					default:
						continuer = false;
					}
				}else
					continuer = false;
			}
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	
	@Override
	public int eval(Entite e,Entite [][] tiles, List<Entite> l, List<EntiteTrace> lTrace){
		int i = actions.get(numeroAction).eval(e, tiles, l, lTrace);
		numeroAction ++;
		numeroAction = numeroAction ++ % actions.size();
		return i;
	}

}
