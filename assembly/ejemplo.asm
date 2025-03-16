TITLE EJEMPLO
.MODEL SMALL
.STACK 100H
.DATA
  nombre       DB 'Carlos',0AH,0DH,'$'
  altura       DD ?
  calificacion DW ?
  msg1         DB 'Ingresa tu calificacion: ',10,13,'$'
  msg2         DB 'Aprobaste',0AH,0DH,'$'
  msg3         DB 'Reprobaste',0AH,0DH,'$'
.CODE
MAIN PROC FAR
.STARTUP
              MOV AX, @DATA
              MOV DS, AX

  ; IMPRIMIR MENSAJE DE ENTRADA
              MOV AH, 09H
              LEA DX, msg1
              INT 21H

              XOR BX, BX
  LEER_NUMERO:
              MOV AH, 1
              INT 21H
              CMP AL,0DH
              JE  SIGUIENTE
    
              AND AL, 0FH
              XOR AH, AH
              MOV CX,AX

              MOV AX, 10
              MUL BX
    
              MOV BX, AX
    
              ADD BX, CX
    
              JMP LEER_NUMERO
  SIGUIENTE:  
              MOV calificacion, BX
  ; COMPARAR
              CMP calificacion, 70
              JGE APROBADO
              JMP REPROBADO

  ; IMPRIMIR RESULTADO
  APROBADO:   
              MOV AH, 09H
              LEA DX, msg2
              INT 21H
              JMP SALIR

  REPROBADO:  
              MOV AH, 09H
              LEA DX, msg3
              INT 21H

  SALIR:      
              MOV AH, 4CH
              INT 21H
.EXIT
MAIN ENDP
END MAIN