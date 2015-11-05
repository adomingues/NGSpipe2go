dupRadar = {
	doc title: "dupRadar",
		desc:  "analysis of duplication rate on RNAseq analysis",
		constraints: "",
		bpipe_version: "tested with bpipe 0.9.8.7",
		author: "Sergi Sayols"

	output.dir = DUPRADAR_OUTDIR
	def DUPRADAR_FLAGS = " gtf="      + DUPRADAR_GTF      +
	                     " stranded=" + DUPRADAR_STRANDED + 
			    		 " paired="   + DUPRADAR_PAIRED   +
						 " outdir="   + DUPRADAR_OUTDIR   +
				    	 " threads="  + Integer.toString(DUPRADAR_THREADS)

	// run the chunk
	transform(".bam") to("_dupRadar.png") {
		exec """
			export TOOL_DEPENDENCIES=$TOOL_DEPENDENCIES &&
			source ${TOOL_R}/env.sh &&
			if [ -n "\$LSB_JOBID" ]; then
				export TMPDIR=/jobdir/\${LSB_JOBID};
			fi &&
			
			echo 'VERSION INFO'  1>&2 ;
			echo \$(${TOOL_R}/bin/Rscript --version 2>&1 | cut -d' ' -f5) 1>&2 ;
			echo '/VERSION INFO'  1>&2 ;
			
			${TOOL_R}/bin/Rscript ${TOOL_DUPRADAR}/dupRadar.R $input $DUPRADAR_FLAGS
		""","dupRadar"
	}

	forward input
}

