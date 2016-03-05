package com.sheremet.spring.AnnotationsLinking;

import java.util.Comparator;

public interface MouseCatComparator extends Comparator<MouseCatPosition>{
	void setMode(int mode);
	int getMode();
}
