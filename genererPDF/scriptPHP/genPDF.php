<?php
require('../fpdf/fpdf.php');
$Nom= $_POST["nom"];
$Prenom= $_POST["prenom"];
$Adresse= $_POST["adresse"];
$pdf = new FPDF(); //FPDP library download it from internet  
$pdf->AddPage();
$pdf->SetFont('Arial','B',32);
$pdf->SetTextColor (0,0,250);
$pdf->Image ('andro.png',10,10,-300);

$pdf->Cell(60);
$pdf->Cell(5,60,'Inscription de Client',0,1);

$pdf->SetFont('Arial','B',16);
$pdf->SetTextColor (0,0,0);

$pdf->Cell(50,20,'Nom     	:',0,0);
$pdf->Cell(50,20,$Nom,0,1);
$pdf->Cell(50,20,'Prenom  :',0,0);
$pdf->Cell(50,20,$Prenom,0,1);
$pdf->Cell(50,20,'Adresse :',0,0);
$pdf->Cell(50,20,$Adresse,0,1);


  
$pdf->Output("Inscription.pdf",'F');
echo "OK";
?>
