package com.mehnadnerd.EmpireGame.things;

import java.util.Random;

public class EmpirePlanet extends EmpireThing {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Biome biome;
	private int metalProd;
	private int fuelProd;
	private int foodProd;

	public EmpirePlanet() {

	}

	public EmpirePlanet(String name, int metalProd, int fuelProd, int foodProd, Biome biome, EmpireLocationRef loc) {
		super.owner = EmpirePlayer.getDefault();
		super.name = name;
		this.metalProd = metalProd;
		this.fuelProd = fuelProd;
		this.foodProd = foodProd;
		this.biome = biome;
		this.loc = loc;
	}

	public EmpirePlanet(String name, int metalProd, int fuelProd, int foodProd, Biome biome, EmpireLocationRef loc,
			EmpirePlayer owner) {
		super.owner = owner;
		super.name = name;
		this.metalProd = metalProd;
		this.fuelProd = fuelProd;
		this.foodProd = foodProd;
		this.biome = biome;
		this.loc = loc;
	}

	public EmpirePlanet(int metalProd, int fuelProd, int foodProd, Biome biome, EmpireLocationRef loc) {
		super.owner = EmpirePlayer.getDefault();
		this.metalProd = metalProd;
		this.fuelProd = fuelProd;
		this.foodProd = foodProd;
		this.biome = biome;
		super.name = this.genName();
		this.loc = loc;
	}

	public Biome getBiome() {
		return biome;
	}

	public void setBiome(Biome biome) {
		this.biome = biome;
	}

	public void setMetalProd(int metalProd) {
		this.metalProd = metalProd;
	}

	public int getMetalProd() {
		return metalProd;
	}

	public void setFuelProd(int fuelProd) {
		this.fuelProd = fuelProd;
	}

	public int getFuelProd() {
		return fuelProd;
	}

	public void setFoodProd(int foodProd) {
		this.foodProd = foodProd;
	}

	public int getFoodProd() {
		return foodProd;
	}

	public static EmpirePlanet randomPlanet(EmpireLocationRef loc) {
		Random r = new Random();
		EmpirePlanet p = null;
		switch (r.nextInt(6)) {
		case 0:
			// forest
			p = new EmpirePlanet(0, 0, genResourceNum(), Biome.FOREST, loc);
			break;
		case 1:
			// jungle
			p = new EmpirePlanet(0, 0, genResourceNum(), Biome.JUNGLE, loc);
			break;
		case 2:
			// desert(fuel)
			p = new EmpirePlanet(0, genResourceNum(), 0, Biome.DESERT, loc);
			break;
		case 3:
			// mountain(metal)
			p = new EmpirePlanet(genResourceNum(), 0, 0, Biome.MOUNT, loc);
			break;
		case 4:
			// tundra
			if (r.nextBoolean()) {
				// metal tundra
				p = new EmpirePlanet(genResourceNum(), 0, 0, Biome.TUNDRA, loc);
			} else {
				// fuel tundra
				p = new EmpirePlanet(0, genResourceNum(), 0, Biome.TUNDRA, loc);
			}
			break;
		case 5:
			// non-planet event-uhhhhh
			//pretending it is a 1-1-1
			p = new EmpirePlanet(1, 1, 1, Biome.OTHER, loc);
			break;
		default:
			p = EmpirePlanet.getDefault();
			break;
		}
		p.setName(p.genName());
		return p;
	}

	public static EmpirePlanet randomPlanet(String name, EmpireLocationRef loc) {
		EmpirePlanet p = randomPlanet(loc);
		p.setName(name);
		return p;
	}

	@Override
	public int getColumnsNeeded() {
		return 6;
	}

	public void setName(String name) {
		super.name = name;
	}

	@Override
	public void updateTick() {
		this.refreshAll();

	}

	@Override
	public String genName() {
		return fancyNameGen(this.biome, this.metalProd+this.fuelProd+this.foodProd);
		//return "Planet" + biome + ":" + metalProd + "," + fuelProd + "," + foodProd;
	}

	@Override
	public Object[] tableForm() {
		Object[] ret = new Object[this.getColumnsNeeded()];
		ret[0] = name;
		ret[1] = this.biome;
		ret[2] = this.metalProd;
		ret[3] = this.fuelProd;
		ret[4] = this.foodProd;
		ret[5] = loc;
		return ret;
	}

	@Override
	public String[] tableHeaders() {
		String[] ret = new String[this.getColumnsNeeded()];
		ret[0] = "Name";
		ret[1] = "Biome";
		ret[2] = "Metal";
		ret[3] = "Fuel";
		ret[4] = "Food";
		ret[5] = "Loc";
		return ret;
	}

	private static int genResourceNum() {
		Random r = new Random();
		switch (r.nextInt(6)) {
		case 0:
			return 1;
		case 1:
			return 1;
		case 2:
			return 2;
		case 3:
			return 2;
		case 4:
			return 3;
		case 5:
			return 4;
		default:
			return 0;
		}
	}

	public enum Biome {
		DESERT, MOUNT, FOREST, JUNGLE, TUNDRA, OTHER, CAPITAL
	}

	public static Biome nameBiome(String name) {
		name=name.toUpperCase();
		switch (name) {
		case "DESERT":
			return Biome.DESERT;
		case "MOUNT":
			return Biome.MOUNT;
		case "MOUNTAIN":
			return Biome.MOUNT;
		case "FOREST":
			return Biome.FOREST;
		case "JUNGLE":
			return Biome.JUNGLE;
		case "TUNDRA":
			return Biome.TUNDRA;
		case "OTHER":
			return Biome.OTHER;
		case "CAPITAL":
			return Biome.CAPITAL;
		}
		return Biome.OTHER;
	}

	public static EmpirePlanet getDefault() {
		return new EmpirePlanet(0, 0, 0, Biome.OTHER, EmpireLocationRef.getDefault());
	}

	@Override
	public String toString() {
		return "EmpirePlanet|" + name + "|B:" + biome + "|" + metalProd + "|" + fuelProd + "|" + foodProd + "|L" + loc;
	}

	@Override
	public void refreshAll() {

	}

	public static String fancyNameGen(Biome b, int resourceProd) {
		String toReturn = "";
		Random r = new Random();
		if (r.nextBoolean()) {
			// Two-word name
			toReturn = intensityToName(resourceProd) + " " + biomeToName(b).toLowerCase();

		} else {
			//should be a one-word name, but just gives two-word for now
			toReturn = intensityToName(resourceProd) + " " + biomeToName(b).toLowerCase();
		}
		toReturn+= (r.nextInt(10000)+1);
		return toReturn;
	}

	private static String biomeToName(Biome b) {
		Random r = new Random();
		switch(b) {
		case DESERT:
			switch (r.nextInt(3)) {
			case 0:
				return "Desert";
			case 1:
				return "Dustplain";
			case 2:
				return "Pile of sand";
			}
		case CAPITAL:
			switch (r.nextInt(3)) {
			case 0:
				return "Capital";
			case 1:
				return "Prime";
			case 2:
				return "Centre";
			}
		case FOREST:
			switch (r.nextInt(3)) {
			case 0:
				return "Plains";
			case 1:
				return "Forest";
			case 2:
				return "Treeland";
			}
		case JUNGLE:
			switch (r.nextInt(3)) {
			case 0:
				return "Jungle";
			case 1:
				return "Rainforest";
			case 2:
				return "Bananaland";
			}
		case MOUNT:
			switch (r.nextInt(3)) {
			case 0:
				return "Mountains";
			case 1:
				return "Peaks";
			case 2:
				return "Heights";
			}
		case OTHER:
			return "Thingy";
		case TUNDRA:
			switch (r.nextInt(3)) {
			case 0:
				return "Tundra";
			case 1:
				return "Siberia";
			case 2:
				return "Iceland";
			}
		default:
			return "Oddity";
			
		}
	}

	private static String intensityToName(int i) {
		Random r = new Random();
		switch (i) {
		case 0:
			switch (r.nextInt(3)) {
			case 0:
				return "Stupid";
			case 1:
				return "Sucky";
			case 2:
				return "Useless";
			}
		case 1:
			switch (r.nextInt(3)) {
			case 0:
				return "Meh";
			case 1:
				return "Acceptable";
			case 2:
				return "Ehhh";
			}
		case 2:
			switch (r.nextInt(3)) {
			case 0:
				return "Average";
			case 1:
				return "Mediocre";
			case 2:
				return "Mild";
			}
		case 3:
			switch (r.nextInt(3)) {
			case 0:
				return "Decent";
			case 1:
				return "Good";
			case 2:
				return "Great";
			}
		case 4:
			switch (r.nextInt(3)) {
			case 0:
				return "Awesome";
			case 1:
				return "Ultra";
			case 2:
				return "Turtwig-blessed";
			}
		case 5:
			return "Superplanetary";
		case 6:
			return "Capitolish";
		default:
			return "Odd";
		}
	}

}