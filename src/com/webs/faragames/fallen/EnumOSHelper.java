package com.webs.faragames.fallen;

public class EnumOSHelper {
	public static final int[] OS = new int[EnumOS.values().length];

	static {
		try {
			OS[EnumOS.LINUX.ordinal()] = 1;
		} catch (NoSuchFieldError nosuchfielderror) {
			;
		}

		try {
			OS[EnumOS.SOLARIS.ordinal()] = 2;
		} catch (NoSuchFieldError nosuchfielderror1) {
			;
		}

		try {
			OS[EnumOS.WINDOWS.ordinal()] = 3;
		} catch (NoSuchFieldError nosuchfielderror2) {
			;
		}

		try {
			OS[EnumOS.MACOS.ordinal()] = 4;
		} catch (NoSuchFieldError nosuchfielderror3) {
		}
	}
}