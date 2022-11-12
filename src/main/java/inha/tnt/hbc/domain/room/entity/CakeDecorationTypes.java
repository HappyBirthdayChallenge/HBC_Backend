package inha.tnt.hbc.domain.room.entity;

import java.util.Random;

public enum CakeDecorationTypes {
	CAKE_TYPE1,
	CAKE_TYPE2,
	CAKE_TYPE3,
	CAKE_TYPE4,
	CAKE_TYPE5,
	CAKE_TYPE6,
	CAKE_TYPE7,
	CAKE_TYPE8,
	CAKE_TYPE9,
	CAKE_TYPE10,
	CAKE_TYPE11,
	;

	public static CakeDecorationTypes random() {
		return CakeDecorationTypes.values()[new Random().nextInt(
			CakeDecorationTypes.values().length)];
	}
}
