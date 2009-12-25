#!/bin/sh
if [ $CLASSPATH=="" ] ; then
  CLASSPATH=/usr/local/jdepend/lib/jdepend-2.9.1.jar:/usr/local/eclipse/plugins/org.junit_3.8.1/junit.jar:/usr/local/eclipse/plugins/net.sourceforge.pmd.eclipse_3.0.1/lib/log4j-1.2.8.jar:/usr/local/ant/lib/xml-apis.jar:/usr/local/ant/lib/xercesImpl.jar:/usr/local/fop/lib/xalan-2.4.1.jar:/usr/local/cobertura/cobertura.jar:/usr/local/jcsc/lib/JCSC.jar;/usr/local/jcsc/lib/gnu-regexp.jar
fi
