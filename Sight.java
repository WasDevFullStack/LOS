package lineofsight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sight {
	
	private static void main(String[] args){
//		System.out.println(Math.cos(Math.toRadians(30)));
//		System.out.println(Math.cos(Math.toRadians(270)));
//		System.out.println(Math.sin(Math.toRadians(0)));
//		System.out.println(Math.sin(Math.toRadians(180)));
		
		
		final int xmax = 50;
		final int ymax = 50;
		int[][] mapa = new int[xmax][ymax];
		int[][] unidades = new int[xmax][ymax];
		Sight s = new Sight(mapa, unidades,xmax,ymax);
		
		UnidadeSight u = new UnidadeSight() {
			
			@Override
			public boolean bloqueado(int[][] mapa, int[][] unidade, int x, int y) {
				if(x < 0 || x >= xmax || y < 0 || y >= ymax){
					return true;
				}
				
				if(mapa[x][y] == 1 || unidade[x][y] == 1){
					return true;
				}
				
				return false;
			}
		};
		
		List<int[]> lista = s.calcular(u,24,15,300,360,25);
		
		s.desenha(lista,xmax,ymax);
		
	}
	
	
	private int[][] mapa;
	
	private int[][] unidades;
	
	private int xmax;
	
	private int ymax;
	
	public Sight(int[][] mapa,int[][] unidades,int xmax,int ymax){
		this.mapa = mapa;
		this.unidades = unidades;
		this.xmax = xmax;
		this.ymax = ymax;
	}
	
	private List<int[]> lista;
	private Map<String,int[]> hMapa;
	
	/**
	 * Faz uma varredura ao redor do personagem
	 * @param u - Unidade que está fazendo a varredura
	 * @param x - Posição da unidade X
	 * @param y - Posição da unidade Y
	 * @param iangulo - Ângulo inicial para varredura Ex: 0
	 * @param angulo - Ângulo final para varredura Ex: 360
	 * @param d - Distância da varredura a partir do ponto do personagem
	 * @return - Lista de unidades encontradas na varredura
	 */
	public List<int[]> calcular(UnidadeSight u,int x,int y,int iangulo,int angulo,int d){
				
		Map<String,int[]> hMapa = new HashMap<String,int[]>();
		
		int ai = iangulo;
		int amax = angulo;

				
		for(; ai <= amax ; ai++){
			

			double rad = Math.toRadians(ai);
			
			double seno = Math.sin(rad);
			
			double coseno = Math.cos(rad);
			
			for(int distancia = 0; distancia <= d; distancia++){
				
				double vx = distancia * coseno;
				
				double vy = distancia * seno;
				
				long mx = Math.round(vx);
				
				long my = Math.round(vy);
				
				int imx = Integer.valueOf(String.valueOf(mx));
				
				int imy = Integer.valueOf(String.valueOf(my));

				
				int X = x + imx;
				
				int Y = y + imy;
				
				if(!isCoord(X, Y)){
					break;
				}
				
				if(!u.bloqueado(mapa,unidades,X,Y)){
					if(X != x || Y != y){
						String chave = "#{"+X+","+Y+"}";
						if(!hMapa.containsKey(chave)){
							hMapa.put(chave,new int[]{X,Y,mapa[X][Y],unidades[X][Y]});
						}
					}										
				}else{
					break;
				}
			}		
		}
		this.lista = new ArrayList<int[]>(hMapa.values());
		
		this.hMapa = hMapa;
		
		return this.lista;
	}
	
	
	/**
	 * Faz uma varredura ao redor do personagem, procurando pela unidade desejada 
	 * @param idUnidade - id da unidade que está procurando
	 * @param x - Posição da unidade X
	 * @param y - Posição da unidade Y
	 * @param iangulo - Ângulo inicial para varredura Ex: 0
	 * @param angulo - Ângulo final para varredura Ex: 360
	 * @param d - Distância da varredura a partir do ponto do personagem
	 * @return - Lista de unidades encontradas na varredura
	 */
	public List<int[]> filtrarUnidade(int idUnidade,int x,int y,int iangulo,int angulo,int d){
		
		Map<String,int[]> hMapa = new HashMap<String,int[]>();
		
		int ai = iangulo;
		int amax = angulo;

		for(; ai <= amax ; ai++){
			
			double rad = Math.toRadians(ai);
			
			double seno = Math.sin(rad);
			
			double coseno = Math.cos(rad);
			
			for(int distancia = 0; distancia <= d; distancia++){
				
				double vx = distancia * coseno;
				
				double vy = distancia * seno;
				
				long mx = Math.round(vx);
				
				long my = Math.round(vy);
				
				int imx = Integer.valueOf(String.valueOf(mx));
				
				int imy = Integer.valueOf(String.valueOf(my));
				
				int X = x + imx;
				
				int Y = y + imy;
				
				if(!isCoord(X, Y)){
					break;
				}				
				
				if((X != x || Y != y) && idUnidade == unidades[X][Y]){
					String chave = "#{"+X+","+Y+"}";
					if(!hMapa.containsKey(chave)){
						hMapa.put(chave,new int[]{X,Y,mapa[X][Y],unidades[X][Y]});
					}
				}			
			}		
		}
		this.lista = new ArrayList<int[]>(hMapa.values());
		
		this.hMapa = hMapa;
		
		return this.lista;
	}
	
	/**
	 * Faz uma varredura ao redor do personagem, procurando pelo terreno desejado 
	 * @param idTerreno - id do terreno que está buscando
	 * @param x - Posição da unidade X
	 * @param y - Posição da unidade Y
	 * @param iangulo - Ângulo inicial para varredura Ex: 0
	 * @param angulo - Ângulo final para varredura Ex: 360
	 * @param d - Distância da varredura a partir do ponto do personagem
	 * @return - Lista de terrenos encontrados na varredura
	 */
	public List<int[]> filtrarTerreno(int idTerreno,int x,int y,int iangulo,int angulo,int d){
		
		Map<String,int[]> hMapa = new HashMap<String,int[]>();
		
		int ai = iangulo;
		int amax = angulo;

		for(; ai <= amax ; ai++){
			
			double rad = Math.toRadians(ai);
			
			double seno = Math.sin(rad);
			
			double coseno = Math.cos(rad);
			
			for(int distancia = 0; distancia <= d; distancia++){
				
				double vx = distancia * coseno;
				
				double vy = distancia * seno;
				
				long mx = Math.round(vx);
				
				long my = Math.round(vy);
				
				int imx = Integer.valueOf(String.valueOf(mx));
				
				int imy = Integer.valueOf(String.valueOf(my));
				
				int X = x + imx;
				
				int Y = y + imy;
				
				if(!isCoord(X, Y)){
					break;
				}				
				
				if((X != x || Y != y) && idTerreno == mapa[X][Y]){
					String chave = "#{"+X+","+Y+"}";
					if(!hMapa.containsKey(chave)){
						hMapa.put(chave,new int[]{X,Y,mapa[X][Y],unidades[X][Y]});
					}
				}			
			}		
		}
		this.lista = new ArrayList<int[]>(hMapa.values());
		
		this.hMapa = hMapa;
		
		return this.lista;
	}
	
	private void desenha(List<int[]> lista,int xmax,int ymax){
		for(int y = 0 ; y < ymax ; y++){
			for(int x = 0 ; x < xmax ; x++){
				if(this.hMapa.containsKey("#{"+x+","+y+"}")){
					System.out.print("1,");
				}else{
					System.out.print("0,");
				}
			}
			System.out.println("");
		}
	}
	
	private boolean isCoord(int x,int y){
		return x < xmax && x >= 0 && y >= 0 && y < ymax;
	}

	public List<int[]> getLista() {
		return lista;
	}

	public Map<String, int[]> gethMapa() {
		return hMapa;
	}


	public int[][] getMapa() {
		return mapa;
	}


	public int[][] getUnidades() {
		return unidades;
	}


	public int getXmax() {
		return xmax;
	}


	public int getYmax() {
		return ymax;
	}

}
