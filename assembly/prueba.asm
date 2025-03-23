TITLE PROGRAMA                             
.MODEL SMALL                               
.386                                       
.STACK 100H                                
.DATA
  a       DW ?
  AUX_CAD DB 255 DUP('$')
.CODE
MAIN PROC FAR
               MOV AX, @DATA
               MOV DS, AX
               MOV a, 0
  MIENTRAS0:   
               MOV AX, a
               CMP AX, 10
               JGE FINMIENTRAS0
               MOV [AUX_CAD + 0], 'H'
               MOV [AUX_CAD + 1], 'o'
               MOV [AUX_CAD + 2], 'l'
               MOV [AUX_CAD + 3], 'a'
               MOV [AUX_CAD + 4], '$'
               MOV AH, 09H
               LEA DX, AUX_CAD
               INT 21H
               MOV AH, 02H
               MOV DL, 0AH
               INT 21H
               MOV DL, 0DH
               INT 21H
               MOV AX, a
               ADD AX, 1
               MOV a, AX
               JMP MIENTRAS0
  FINMIENTRAS0:
               MOV AH, 4CH
               INT 21H
MAIN ENDP
END MAIN                                   
