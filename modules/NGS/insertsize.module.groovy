//rule for task Insertsize from catalog NGS
//desc: get the insert size from a bam paired end bam file
InsertSize = {
	doc title: "InsertSize",
		desc:  "Call picard tools create insert size values",
		constraints: "",
		bpipe_version: "tested with bpipe 0.9.8.7",
		author: "Nastasja Kreim"

	output.dir=QC
	def INSERTSIZE_FLAGS = "ASSUME_SORTED=TRUE"
	def JAVA_FLAGS  = "-Xmx" + INSERTSIZE_MAXMEM + "m"

	transform(".bam") to ("_insersizemetrics.tsv") {
		exec """
			export TOOL_DEPENDENCIES=$TOOL_DEPENDENCIES &&
			source ${TOOL_PICARD}/env.sh &&
			if [ -n "\$LSB_JOBID" ]; then
				export TMPDIR=/jobdir/\${LSB_JOBID};
			fi &&

			echo 'VERSION INFO'  1>&2 ;
			echo \$(java -jar ${TOOL_PICARD}/CollectInsertSizeMetrics.jar --version 2>&1 | cut -d'(' -f1 ) 1>&2 ;
			echo '/VERSION INFO' 1>&2 ;
			
			java $JAVA_FLAGS -jar ${TOOL_PICARD}/CollectInsertSizeMetrics.jar $INSERTSIZE_FLAGS
				INPUT=$input
				OUTPUT=$output
				HISTOGRAM_FILE=${input.prefix}_insertsize_hist.pdf
		""","InsertSize"
	}
}

