//rule for task shinyReports from catalog miscellaneous, version 1
//desc: creates the source code to compile the shiny and markdown reports
shinyReports = {
	doc title: "shinyReports",
		desc:  "creates the source code to compile the shiny and markdown reports",
		constraints: "",
		bpipe_version: "tested with bpipe 0.9.8.7",
		author: "Oliver Drechsel"
	
	output.dir = REPORTS
	
	produce("shinyReports.txt") {
		exec """
			if [ -e "${REPORTS}/variantreport.Rmd" ]; then
				echo 'variantreport.Rmd already exists. Older copy will be kept and not overwritten';
			else
				cp ${MODULE_FOLDER}/../tools/reports/shiny_dnaseq_reporting_tool/variantreport.Rmd ${REPORTS};
			fi &&

			cp ${MODULE_FOLDER}/../tools/reports/shiny_dnaseq_reporting_tool/variant.shinyrep.helpers.R ${REPORTS} &&
			cp ${MODULE_FOLDER}/../tools/reports/shiny_dnaseq_reporting_tool/styles.css ${REPORTS}                 &&
			
			PROJECT=\$(basename ${SHINYREPS_PROJECT})                                 &&
			sed -i "2,2s/SHINYREPS_PROJECT/\${PROJECT}/" ${REPORTS}/variantreport.Rmd &&
			
			echo "SHINYREPS_PROJECT=${SHINYREPS_PROJECT}" >  $output &&
			echo "SHINYREPS_LOG=${SHINYREPS_LOG}"         >> $output &&
			echo "SHINYREPS_QC=${SHINYREPS_QC}"           >> $output &&
			echo "SHINYREPS_RES=${SHINYREPS_RES}"         >> $output &&
			echo "SHINYREPS_PREFIX=${SHINYREPS_PREFIX}"   >> $output &&
			echo "SHINYREPS_FASTQC_OUT=${SHINYREPS_FASTQC_OUT}"     >> $output &&
			echo "SHINYREPS_FASTQC_LOG=${SHINYREPS_FASTQC_LOG}"     >> $output &&
			echo "SHINYREPS_BWA_LOG=${SHINYREPS_BWA_LOG}"           >> $output &&
			echo "SHINYREPS_BWA_SUFFIX=${SHINYREPS_BWA_SUFFIX}"     >> $output &&
			echo "SHINYREPS_MARKDUPS_LOG=${SHINYREPS_MARKDUPS_LOG}" >> $output &&
			echo "SHINYREPS_GATKug_LOG=${SHINYREPS_GATKug_LOG}"           >> $output &&
			echo "SHINYREPS_GATKug_SUFFIX=${SHINYREPS_GATKug_SUFFIX}"     >> $output &&
			echo "SHINYREPS_GATKhc_LOG=${SHINYREPS_GATKhc_LOG}"           >> $output &&
			echo "SHINYREPS_GATKhc_SUFFIX=${SHINYREPS_GATKhc_SUFFIX}"     >> $output &&
			echo "SHINYREPS_GATKvarianteval=${SHINYREPS_GATKvarianteval}"           >> $output &&
			echo "SHINYREPS_GATKvarianteval_SUFFIX=${SHINYREPS_GATKvarianteval_SUFFIX}"       >> $output &&
			echo "SHINYREPS_GATKhc_SUFFIX=${SHINYREPS_GATKhc_SUFFIX}"       >> $output
			
		""","shinyReports"
	}
}

