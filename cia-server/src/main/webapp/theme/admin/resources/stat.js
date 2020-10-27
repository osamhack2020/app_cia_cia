/**
 * 
 */
var stat1 = document.getElementById('stat1').getContext('2d');
var stat2 = document.getElementById('stat2').getContext('2d');

var stat3 = document.getElementById('stat3').getContext('2d');
var stat4 = document.getElementById('stat4').getContext('2d');

var stat5 = document.getElementById('stat5').getContext('2d');
var stat6 = document.getElementById('stat6').getContext('2d');

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
                        backgroundColor: ['rgba(255, 99, 132, 0.2)','rgba(54, 162, 235, 0.2)','rgba(255, 206, 86, 0.2)','rgba(75, 192, 192, 0.2)','rgba(153, 102, 255, 0.2)','rgba(255, 159, 64, 0.2)', 'rgba(255,255,255,0.2)'],
                        borderColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)', 'rgba(255, 159, 64, 1)', 'rgba(255,255,255,1)'],
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
                type: 'doughnut',
                data: {
                    labels: labels,
                    datasets: [{
                        label: '비중',
                        labels: labels,
                        sum: sum,
                        data: datas,
                        backgroundColor: ['rgba(255, 99, 132, 0.2)','rgba(54, 162, 235, 0.2)','rgba(255, 206, 86, 0.2)','rgba(75, 192, 192, 0.2)','rgba(153, 102, 255, 0.2)','rgba(255, 159, 64, 0.2)', 'rgba(255,255,255,0.2)'],
                        borderColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)', 'rgba(255, 159, 64, 1)', 'rgba(255,255,255,1)'],
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



function loadStat3() {
    $.ajax({ 
        type : "GET",
        dataType : "json",
        url : "/api/stat/3",	           
        success : function(data){
            
            var labels = [];
            var datas = [];
            var sum = 0;
            $(data.list).each(function(i,d){
            	labels.push(d.name);
            	datas.push(d.count);
            	sum = d.sum;
            });
             
            new Chart(stat3, {
                type: 'doughnut',
                data: {
                    labels: labels,
                    datasets: [{
                        label: '비중',
                        labels: labels,
                        sum: sum,
                        data: datas, 
                        backgroundColor: ['rgba(255, 99, 132, 0.2)','rgba(54, 162, 235, 0.2)','rgba(255, 206, 86, 0.2)','rgba(75, 192, 192, 0.2)','rgba(153, 102, 255, 0.2)','rgba(255, 159, 64, 0.2)', 'rgba(255,255,255,0.2)'],
                        borderColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)', 'rgba(255, 159, 64, 1)', 'rgba(255,255,255,1)'],
                        borderWidth: 1
                    }]
                }               
            });

        }, 
        error : function(e1,e2,e3){
            console.log(e1);console.log(e2);console.log(e3);
        } 
    });	
    
}
loadStat3();

function loadStat4() {
    $.ajax({ 
        type : "GET",
        dataType : "json",
        url : "/api/stat/4",	           
        success : function(data){
            
            var labels = [];
            var datas = [];
            var sum = 0;
            $(data.list).each(function(i,d){
            	labels.push(d.name); 
            	datas.push(d.count);
            	sum = d.sum;
            });
             
            new Chart(stat4, {
                type: 'pie',
                data: {
                    labels: labels,
                    datasets: [{
                        label: '비중',
                        labels: labels,
                        sum: sum,
                        data: datas, 
                        backgroundColor: ['rgba(255, 99, 132, 0.2)','rgba(54, 162, 235, 0.2)','rgba(255, 206, 86, 0.2)','rgba(75, 192, 192, 0.2)','rgba(153, 102, 255, 0.2)','rgba(255, 159, 64, 0.2)', 'rgba(255,255,255,0.2)'],
                        borderColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)', 'rgba(255, 159, 64, 1)', 'rgba(255,255,255,1)'],
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
loadStat4();






//============================================

function loadStat5() {
    $.ajax({ 
        type : "GET",
        dataType : "json",
        url : "/api/stat/5",	           
        success : function(data){
            
            var labels = [];
            var datas = [];
            var sum = 0;
            $(data.list).each(function(i,d){
            	console.log(d);
            	labels.push(d.joinPath); 
            	datas.push(d.count);
            	sum = d.sum;
            });
             
            new Chart(stat5, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: '회원 수',
                        labels: labels,
//                        sum: sum,
                        data: datas, 
                        backgroundColor: ['rgba(255, 99, 132, 0.2)','rgba(54, 162, 235, 0.2)','rgba(255, 206, 86, 0.2)','rgba(75, 192, 192, 0.2)','rgba(153, 102, 255, 0.2)','rgba(255, 159, 64, 0.2)','rgba(255,255,255,0.2)'],
                        borderColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)', 'rgba(255, 159, 64, 1)', 'rgba(255,255,255,1)'],
                        borderWidth: 1
                    }]
                }
            });

        }, 
        error : function(e1,e2,e3){
            console.log(e1);console.log(e2);console.log(e3);
        } 
    });	
    
}
loadStat5();


function loadStat6() {
    $.ajax({ 
        type : "GET",
        dataType : "json",
        url : "/api/stat/6",	           
        success : function(data){
            
            var labels = [];
            var datas = [];
            var sum = 0;
            $(data.list).each(function(i,d){
            	console.log(d);
            	labels.push(d.date); 
            	datas.push(d.count);
            	sum = d.sum;
            });
             
            new Chart(stat6, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        label: '가입자 수',
                        labels: labels,
//                        sum: sum,
                        data: datas, 
                        backgroundColor: ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)','rgba(255, 206, 86, 0.2)','rgba(75, 192, 192, 0.2)','rgba(153, 102, 255, 0.2)','rgba(255, 159, 64, 0.2)'],
                        borderColor: ['rgba(54, 162, 235, 1)', 'rgba(255, 99, 132, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)', 'rgba(255, 159, 64, 1)'],
                        borderWidth: 1
                    }]
                }
            });

        }, 
        error : function(e1,e2,e3){
            console.log(e1);console.log(e2);console.log(e3);
        } 
    });	
    
}
loadStat6();