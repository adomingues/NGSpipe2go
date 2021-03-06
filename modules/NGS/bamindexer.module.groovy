//rule for task BAMindexer from catalog NGS, version 1
//desc: Samtools index a bam file
BAMindexer = {
	doc title: "BAMindexer",
		desc:  "Call samtools to index a bam file",
		constraints: "Define a global SAMTOOLS var pointing to the bin file",
		bpipe_version: "tested with bpipe 0.9.8.7",
		author: "Sergi Sayols"

	output.dir = MAPPED

	transform(".bam") to(".bam.bai") {
		exec """
			export TOOL_DEPENDENCIES=$TOOL_DEPENDENCIES &&
			if [ -n "\$LSB_JOBID" ]; then
				export TMPDIR=/jobdir/\${LSB_JOBID};
			fi &&
			
			echo 'VERSION INFO'  1>&2 &&
			echo \$(${TOOL_SAMTOOLS} --version | grep samtools | cut -d' ' -f2) 1>&2 &&
			echo '/VERSION INFO' 1>&2 &&
			
			${TOOL_SAMTOOLS} index $input
		""","BAMindexer"
	}

	forward input
}

