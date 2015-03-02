public class SkipListALG implements Sem2Interface
{
		class SkipListNode{
		String key;
		SkipListNode[] next;
		
		public SkipListNode(String kljuc, int visina){
			key = kljuc;
			next = new SkipListNode[visina];
		}
		}
	
	private int count = 0;//stevilo elementov v preskocnem seznamu 
	private SkipListNode head;//glava preskocnega seznama
	private int maxLevel = 32;//maksimalno stevilo nivojev
	SkipListNode[] finger = new SkipListNode[maxLevel];//zapomni si elemente pri vsakem iskanju

	
	//konstruktor - glava z visino maxLevel kaze na null
	public SkipListALG() {
		head = new SkipListNode("", maxLevel);
		for(int i=0; i<maxLevel; i++){
			head.next[i] = null;
		}
		count = 0;
	}
		
	
/*	//po nivojih in s preskakovanjem isce iskani element,
	//ce je noter, vrne True, sicer False
	//povprecna cas. zaht.: O(log n); n je st. elementov v preskocnem seznamu
	//najslabsa cas. zaht.: O(n)
	@Override
	public boolean contains(String s) {
		if(head.next[0] == null){
			return false;
		}
		SkipListNode node = head;
		for(int i = maxLevel-1; i >=0;i--){
			while(node.next[i] != null && node.next[i].key.compareTo(s) < 0){
				node = node.next[i];
			}
			if(node.next[i] != null && node.next[i].key.compareTo(s) == 0){
					return true;		
			}
			if(node.next[0] != null && node.next[0].key.compareTo(s) > 0){
					return false;
			}
		}
		return false;
	}
*/	
	
	//vrne st. elementov v pres. seznamu
	//O(1)
	public int getCount() 
	{
		return count;
	}


	//isce element v preskocnem seznamu s pomocjo pomnenja iz prejsnjih iskanj,
	//ce je element vsebovan vrne True, sicer False
	//pricakovana cas. zaht.: O(log k); k je razdalja med prejsnjim iskanim elementom in trenutnim iskanim
	//najslabsa cas. zaht.: O(n); n je st. elementov v preskocnem seznamu
	@Override
	public boolean contains(String s){
		if(head.next[0] == null){
			return false;
		}
		int lvl = 1;
		SkipListNode x = head;
		if(finger[0] != null && finger[0].key.compareTo(s) < 0){
			while(lvl <= maxLevel && finger[lvl].next[lvl] != null && finger[lvl].next[lvl].key.compareTo(s) < 0){
				lvl++;
			}
			x = finger[lvl];
		}	
		else{
			while(lvl < maxLevel && finger[lvl] != null && finger[lvl].key.compareTo(s) >=0){
				lvl = lvl+1;
			}
			if(lvl > maxLevel){
				lvl = maxLevel;
				x = head;
			}
			else{
				if(finger[lvl] != null) x = finger[lvl];
				else {x=head; lvl = maxLevel;}
			}
		}	
		
		for(int j = lvl-1; j >=0; j--){
			while(x.next[j] != null && x.next[j].key.compareTo(s) < 0){
				x = x.next[j];
			}
			finger[j] = x;
			if(x.next[j] != null && x.next[j].key.compareTo(s) == 0){
				return true;		
			}
			if(x.next[0] != null && x.next[0].key.compareTo(s) > 0){
				return false;
			}
		}
		
		return false;
	}
	
	
	//poisce kazalce, ki kazejo na mesto, kjer bo novi element dodan
	//doda nov element in uredi kazalce do dodanega elementa in iz njega in vrne True
	//ce element ze obstaja v strukturi, samo vrne False
	//sproti se preverja, da ne primerja istih elementov veckrat
	//povprecna cas. zaht.: O(log n); n je st. elementov v preskocnem seznamu
	//najslabsa cas. zaht.: O(n)
	@Override
	public boolean add(String s) {
		SkipListNode node = head;
		SkipListNode checked = null;
		SkipListNode[] update = new SkipListNode[maxLevel];
		for(int i = maxLevel-1; i>=0; i--){
			while(node.next[i] != checked && node.next[i] != null && node.next[i].key.compareTo(s) < 0){
				node = node.next[i];
			}
			checked = node.next[i];
			update[i] = node;
		}
		if(node.next[0] != null && node.next[0].key.compareTo(s) == 0){
				return false;			
		}
		int v = visina();	
		SkipListNode novo = new SkipListNode(s, v);
		for(int i=0; i<v; i++){
			novo.next[i] = update[i].next[i];
			update[i].next[i] = novo;
		}
		count++;
		return true;
	}
	
	//izracuna visino za nov dodani element
	public int visina(){
		int visina = 1;
		while(visina < maxLevel && Math.random()>0.5){
			visina++;
		}
		return visina;
	}
	
	
	
	//poisce kazalce na element a in kazalce na element b
	//preveze kazalce, ki so kazali na element a, na elemente, na katere je kazal element b
	//sproti se preverja, da ne primerja istih elementov veckrat
	//povprecna cas. zaht.: O(log n); n je st. elementov v preskocnem seznamu
	//najslabsa cas. zaht.: O(n)
	@Override
	public void removeBetween(String a, String b) {
		if(head.next[0] == null){
			return;
		}
		SkipListNode node = head;
		SkipListNode node2 = head;
		SkipListNode checked = null;
		SkipListNode checked2 = null;
		int c = 0;
		SkipListNode[] poljea = new SkipListNode[maxLevel];
		SkipListNode[] poljeb = new SkipListNode[maxLevel];
		for(int i = maxLevel-1; i>=0; i--){
			while(node.next[i] != checked && node.next[i] != null && node.next[i].key.compareTo(a) < 0){
				node = node.next[i];
			}
			checked = node.next[i];
			poljea[i] = node;
			while(node2.next[i] != checked2 && node2.next[i] != null && node2.next[i].key.compareTo(b) <= 0){
				node2 = node2.next[i];
			}
			checked2 = node2.next[i];
			poljeb[i] = node2;			
		}
		while(node.key != node2.key)
		{
			node=node.next[0];
			c++;
		}
		count = count - c;
		for(int j=0; j<maxLevel-1; j++){				
			poljea[j].next[j] = poljeb[j].next[j];
			}
		
			return;
		}
		

	
	//poisce kazalce pred elementom, ki bo odstranjen in jih 
	//preveze na elemente na katere izbrisani element kaze in vrne True
	//ce elementa ni v strukturi vrne False
	//sproti se preverja, da ne primerja istih elementov veckrat
	//povprecna cas. zaht.: O(log n); n je st. elementov v preskocnem seznamu
	//najslabsa cas. zaht.: O(n)	
	@Override
	public boolean remove(String s) {
		if(head.next[0] == null){
			return false;
		}
		SkipListNode node = head;
		SkipListNode checked = null;
		SkipListNode[] update = new SkipListNode[maxLevel];
		for(int i = maxLevel-1; i >=0;i--){
			while(node.next[i] != checked && node.next[i] != null && node.next[i].key.compareTo(s) < 0){
				node = node.next[i];
			}
			checked = node.next[i];
			update[i] = node;
		}	
			if(node.next[0] != null && node.next[0].key.compareTo(s) == 0){
					node = node.next[0];
					for(int j=0; j<maxLevel; j++){
						if(update[j].next[j] == node){					
							update[j].next[j] = node.next[j];
						}
					}
					count --;
					return true;
			}		
			if(node.next[0] != null){
				if(node.next[0].key.compareTo(s) > 0){
					return false;
				}
			}
		
		return false;
	}
	
	
	//zbrise vse elemente iz strukture
	//O(1)
	@Override
	public void clear() {
		for(int i=0; i<maxLevel; i++){
			head.next[i] = null;
			finger[i] = null;
		}
		count = 0;
	}



	//elemente iz seznama prepise v polje
	//O(n); n je st. elementov v preskocnem seznamu
	@Override
	public String[] toArray() {
		String[] temp = new String[count];
		SkipListNode node = head;
		int i = 0;
		while(node.next[0] != null){
			node = node.next[0];
			temp[i] = node.key;
			i++;	
		}
		return temp;
	}
		

	public String toString()
	{
		String s = "";
		String[] temp = this.toArray();
		for (int i = 0; i < temp.length; i++) s += (temp[i] + " "); 
		return s;	
	}


	@Override
	public String studentId() {
		return "27122003";
	}

}
