<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
       
<c:set var="RESOURCES_VER"><spring:eval expression="@siteinfo['app.resource.ver']"></spring:eval></c:set>


<style>
.ll {text-align:left !important;}
#list_target_1 td {text-align:left !important;}
#list_target_1 td.cc {text-align:center !important; font-weight: bold;}
</style>
<div class="contents_wrapper">
    <div class="layer">

        <div class="headline">Category Stat</div>

        <div class="paddingbox">
            <div>
            
            	<div style="width: 800px; margin: 0px auto;">
            		<div style="width: 400px; height: 400px; float:left;">
	            		<div style="padding-bottom: 20px;">
	            			<strong style="font-size: 18px;">카테고리별 모임 수강 점유율</strong>
	            		</div>
	            		<canvas id="stat1" width="400" height="400"></canvas>
	            	</div>
	            	<div style="width: 400px; height: 400px; float:left;">
	            		<div style="padding-bottom: 20px;">
	            			<strong style="font-size: 18px;">카테고리별 클래스 수강 점유율</strong>
	            		</div>
	            		<canvas id="stat2" width="400" height="400"></canvas>
	            	</div>	         
            	</div>
            
            
            	
				

<script>
var stat1 = document.getElementById('stat1').getContext('2d');
var stat2 = document.getElementById('stat2').getContext('2d');

// var stat3 = document.getElementById('stat3').getContext('2d');
/* var myChart = new Chart(ctx, {
    type: 'pie',
    data: {
        labels: ['IT/Programming', 'Pictures', 'Play', '', 'Purple', 'Orange'],
        datasets: [{
            label: '# of Votes',
            data: [12, 19, 3, 5, 2, 3],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
        }]
    } 
}); */
</script>	
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
               
                



            </div>
        </div>

    </div>
</div>	



<script>
function loadStat1() {
    $.ajax({ 
        type : "GET",
        dataType : "json",
        url : "/api/stat/1",	           
        success : function(data){
//             console.log(data.list);
            
            var labels = [];
            var datas = [];
            var sum = 0;
            $(data.list).each(function(i,d){
            	labels.push(d.catName);
            	datas.push(d.count);
            	sum = d.sum;
            });
//             console.log(labels);
//             console.log(datas);
            
            new Chart(stat1, {
                type: 'pie',
                data: {
                    labels: labels, // ['IT/Programming', 'Pictures', 'Play', '', 'Purple', 'Orange'],
                    datasets: [{
                        label: '# of Votes',
                        labels: labels,
                        sum: sum,
                        data: datas, //[12, 19, 3, 5, 2, 3],
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options : {
                	tooltips: {
                        callbacks: {
                            label: function(tooltipItem, data) {
                                var label = data.datasets[tooltipItem.datasetIndex].labels[tooltipItem.index] || '';
                                if (label) {label += ': ';}                       
                                var tmpSum = data.datasets[tooltipItem.datasetIndex].sum;
                                var tmpCount = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                                
                                label += Math.round(tmpCount/tmpSum*100);
								label += "% (" + tmpCount + "회 수강)";
                                return label;
                            }
                        }
                    }
                }
            });

        }, 
        error : function(e1,e2,e3){
            console.log(e1);console.log(e2);console.log(e3);
        } 
    });	
    
}
loadStat1();



function loadStat2() {
    $.ajax({ 
        type : "GET",
        dataType : "json",
        url : "/api/stat/2",	           
        success : function(data){
            
            var labels = [];
            var datas = [];
            var sum = 0;
            $(data.list).each(function(i,d){
            	labels.push(d.catName);
            	datas.push(d.count);
            	sum = d.sum;
            });
            
            new Chart(stat2, {
                type: 'pie',
                data: {
                    labels: labels,
                    datasets: [{
                        label: '비중',
                        labels: labels,
                        sum: sum,
                        data: datas,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options : {
                	tooltips: {
                        callbacks: {
                            label: function(tooltipItem, data) {
                                var label = data.datasets[tooltipItem.datasetIndex].labels[tooltipItem.index] || '';
                                if (label) {label += ': ';}                       
                                var tmpSum = data.datasets[tooltipItem.datasetIndex].sum;
                                var tmpCount = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                                
                                label += Math.round(tmpCount/tmpSum*100);
								label += "% (" + tmpCount + "회 수강)";
                                return label;
                            }
                        }
                    }
                }
            });

        }, 
        error : function(e1,e2,e3){
            console.log(e1);console.log(e2);console.log(e3);
        } 
    });	
    
}
loadStat2();
</script>






