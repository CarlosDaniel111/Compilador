TITLE PROGRAMA                             
.MODEL SMALL                               
.STACK 100H                                
.DATA
  a      DW ?
  b      DW ?
  c      DW ?
  nombre DB 255 DUP('$')
  msg    DB 255 DUP('$')
.CODE
MAIN PROC FAR
              MOV  AX, @DATA
              MOV  DS, AX
              MOV  [msg + 0], 'I'
              MOV  [msg + 1], 'n'
              MOV  [msg + 2], 'g'
              MOV  [msg + 3], 'r'
              MOV  [msg + 4], 'e'
              MOV  [msg + 5], 's'
              MOV  [msg + 6], 'a'
              MOV  [msg + 7], ' '
              MOV  [msg + 8], 't'
              MOV  [msg + 9], 'u'
              MOV  [msg + 10], ' '
              MOV  [msg + 11], 'n'
              MOV  [msg + 12], 'o'
              MOV  [msg + 13], 'm'
              MOV  [msg + 14], 'b'
              MOV  [msg + 15], 'r'
              MOV  [msg + 16], 'e'
              MOV  [msg + 17], '$'
              MOV  AH, 09H
              LEA  DX, msg
              INT  21H
              MOV  AH, 02H
              MOV  DL, 0AH
              INT  21H
              MOV  DL, 0DH
              INT  21H
              MOV  SI, 0
  LEERCAD0:   
              MOV  AH, 01H
              INT  21H
              CMP  AL, 0DH
              JE   FINLEERCAD0
              MOV  [nombre+SI], AL
              INC  SI
              JMP  LEERCAD0
  FINLEERCAD0:
              MOV  [nombre+SI], '$'
              MOV  [msg + 0], 'I'
              MOV  [msg + 1], 'n'
              MOV  [msg + 2], 'g'
              MOV  [msg + 3], 'r'
              MOV  [msg + 4], 'e'
              MOV  [msg + 5], 's'
              MOV  [msg + 6], 'a'
              MOV  [msg + 7], ' '
              MOV  [msg + 8], 'e'
              MOV  [msg + 9], 'l'
              MOV  [msg + 10], ' '
              MOV  [msg + 11], 'p'
              MOV  [msg + 12], 'r'
              MOV  [msg + 13], 'i'
              MOV  [msg + 14], 'm'
              MOV  [msg + 15], 'e'
              MOV  [msg + 16], 'r'
              MOV  [msg + 17], ' '
              MOV  [msg + 18], 'n'
              MOV  [msg + 19], 'u'
              MOV  [msg + 20], 'm'
              MOV  [msg + 21], 'e'
              MOV  [msg + 22], 'r'
              MOV  [msg + 23], 'o'
              MOV  [msg + 24], '$'
              MOV  AH, 09H
              LEA  DX, msg
              INT  21H
              MOV  AH, 02H
              MOV  DL, 0AH
              INT  21H
              MOV  DL, 0DH
              INT  21H
              MOV  BX,0
  LEERNUM0:   
              MOV  AH, 01H
              INT  21H
              CMP  AL, 0DH
              JE   FINLEERNUM0
              SUB  AL, 48
              MOV  AH, 0
              MOV  CX, AX
              MOV  AX, 10
              MUL  BX
              MOV  BX, AX
              ADD  BX, CX
              JMP  LEERNUM0
  FINLEERNUM0:
              MOV  a, BX
              MOV  [msg + 0], 'I'
              MOV  [msg + 1], 'n'
              MOV  [msg + 2], 'g'
              MOV  [msg + 3], 'r'
              MOV  [msg + 4], 'e'
              MOV  [msg + 5], 's'
              MOV  [msg + 6], 'a'
              MOV  [msg + 7], ' '
              MOV  [msg + 8], 'e'
              MOV  [msg + 9], 'l'
              MOV  [msg + 10], ' '
              MOV  [msg + 11], 's'
              MOV  [msg + 12], 'e'
              MOV  [msg + 13], 'g'
              MOV  [msg + 14], 'u'
              MOV  [msg + 15], 'n'
              MOV  [msg + 16], 'd'
              MOV  [msg + 17], 'o'
              MOV  [msg + 18], ' '
              MOV  [msg + 19], 'n'
              MOV  [msg + 20], 'u'
              MOV  [msg + 21], 'm'
              MOV  [msg + 22], 'e'
              MOV  [msg + 23], 'r'
              MOV  [msg + 24], 'o'
              MOV  [msg + 25], '$'
              MOV  AH, 09H
              LEA  DX, msg
              INT  21H
              MOV  AH, 02H
              MOV  DL, 0AH
              INT  21H
              MOV  DL, 0DH
              INT  21H
              MOV  BX,0
  LEERNUM1:   
              MOV  AH, 01H
              INT  21H
              CMP  AL, 0DH
              JE   FINLEERNUM1
              SUB  AL, 48
              MOV  AH, 0
              MOV  CX, AX
              MOV  AX, 10
              MUL  BX
              MOV  BX, AX
              ADD  BX, CX
              JMP  LEERNUM1
  FINLEERNUM1:
              MOV  b, BX
              MOV  [msg + 0], 'B'
              MOV  [msg + 1], 'I'
              MOV  [msg + 2], 'E'
              MOV  [msg + 3], 'N'
              MOV  [msg + 4], 'V'
              MOV  [msg + 5], 'E'
              MOV  [msg + 6], 'N'
              MOV  [msg + 7], 'I'
              MOV  [msg + 8], 'D'
              MOV  [msg + 9], 'O'
              MOV  [msg + 10], ' '
              MOV  [msg + 11], 'A'
              MOV  [msg + 12], ' '
              MOV  [msg + 13], 'L'
              MOV  [msg + 14], 'A'
              MOV  [msg + 15], ' '
              MOV  [msg + 16], 'M'
              MOV  [msg + 17], 'E'
              MOV  [msg + 18], 'J'
              MOV  [msg + 19], 'O'
              MOV  [msg + 20], 'R'
              MOV  [msg + 21], ' '
              MOV  [msg + 22], 'C'
              MOV  [msg + 23], 'A'
              MOV  [msg + 24], 'L'
              MOV  [msg + 25], 'C'
              MOV  [msg + 26], 'U'
              MOV  [msg + 27], 'L'
              MOV  [msg + 28], 'A'
              MOV  [msg + 29], 'D'
              MOV  [msg + 30], 'O'
              MOV  [msg + 31], 'R'
              MOV  [msg + 32], 'A'
              MOV  [msg + 33], '$'
              MOV  AH, 09H
              LEA  DX, msg
              INT  21H
              MOV  AH, 02H
              MOV  DL, 0AH
              INT  21H
              MOV  DL, 0DH
              INT  21H
              MOV  AH, 09H
              LEA  DX, nombre
              INT  21H
              MOV  AH, 02H
              MOV  DL, 0AH
              INT  21H
              MOV  DL, 0DH
              INT  21H
              MOV  AX, a
              ADD  AX, b
              MOV  c, AX
              MOV  [msg + 0], 'L'
              MOV  [msg + 1], 'a'
              MOV  [msg + 2], ' '
              MOV  [msg + 3], 's'
              MOV  [msg + 4], 'u'
              MOV  [msg + 5], 'm'
              MOV  [msg + 6], 'a'
              MOV  [msg + 7], ' '
              MOV  [msg + 8], 'e'
              MOV  [msg + 9], 's'
              MOV  [msg + 10], ':'
              MOV  [msg + 11], '$'
              MOV  AH, 09H
              LEA  DX, msg
              INT  21H
              MOV  AH, 02H
              MOV  DL, 0AH
              INT  21H
              MOV  DL, 0DH
              INT  21H
              MOV  AX, c
              MOV  BX, 10
              MOV  CX, 0
  PUSHNUM0:   
              MOV  DX, 0
              DIV  BX
              ADD  DL, 48
              PUSH DX
              INC  CX
              CMP  AX, 0
              JNE  PUSHNUM0
  MOSTRARNUM0:
              MOV  DX, 0
              POP  DX
              MOV  AH, 02H
              INT  21H
              LOOP MOSTRARNUM0
              MOV  AH, 02H
              MOV  DL, 0AH
              INT  21H
              MOV  DL, 0DH
              INT  21H
              MOV  AX, a
              SUB  AX, b
              MOV  c, AX
              MOV  [msg + 0], 'L'
              MOV  [msg + 1], 'a'
              MOV  [msg + 2], ' '
              MOV  [msg + 3], 'r'
              MOV  [msg + 4], 'e'
              MOV  [msg + 5], 's'
              MOV  [msg + 6], 't'
              MOV  [msg + 7], 'a'
              MOV  [msg + 8], ' '
              MOV  [msg + 9], 'e'
              MOV  [msg + 10], 's'
              MOV  [msg + 11], ':'
              MOV  [msg + 12], '$'
              MOV  AH, 09H
              LEA  DX, msg
              INT  21H
              MOV  AH, 02H
              MOV  DL, 0AH
              INT  21H
              MOV  DL, 0DH
              INT  21H
              MOV  AX, c
              MOV  BX, 10
              MOV  CX, 0
  PUSHNUM1:   
              MOV  DX, 0
              DIV  BX
              ADD  DL, 48
              PUSH DX
              INC  CX
              CMP  AX, 0
              JNE  PUSHNUM1
  MOSTRARNUM1:
              MOV  DX, 0
              POP  DX
              MOV  AH, 02H
              INT  21H
              LOOP MOSTRARNUM1
              MOV  AH, 02H
              MOV  DL, 0AH
              INT  21H
              MOV  DL, 0DH
              INT  21H
              MOV  AX, a
              MOV  BX, b
              MUL  BX
              MOV  c, AX
              MOV  [msg + 0], 'L'
              MOV  [msg + 1], 'a'
              MOV  [msg + 2], ' '
              MOV  [msg + 3], 'm'
              MOV  [msg + 4], 'u'
              MOV  [msg + 5], 'l'
              MOV  [msg + 6], 't'
              MOV  [msg + 7], 'i'
              MOV  [msg + 8], 'p'
              MOV  [msg + 9], 'l'
              MOV  [msg + 10], 'i'
              MOV  [msg + 11], 'c'
              MOV  [msg + 12], 'a'
              MOV  [msg + 13], 'c'
              MOV  [msg + 14], 'i'
              MOV  [msg + 15], 'o'
              MOV  [msg + 16], 'n'
              MOV  [msg + 17], ' '
              MOV  [msg + 18], 'e'
              MOV  [msg + 19], 's'
              MOV  [msg + 20], ':'
              MOV  [msg + 21], '$'
              MOV  AH, 09H
              LEA  DX, msg
              INT  21H
              MOV  AH, 02H
              MOV  DL, 0AH
              INT  21H
              MOV  DL, 0DH
              INT  21H
              MOV  AX, c
              MOV  BX, 10
              MOV  CX, 0
  PUSHNUM2:   
              MOV  DX, 0
              DIV  BX
              ADD  DL, 48
              PUSH DX
              INC  CX
              CMP  AX, 0
              JNE  PUSHNUM2
  MOSTRARNUM2:
              MOV  DX, 0
              POP  DX
              MOV  AH, 02H
              INT  21H
              LOOP MOSTRARNUM2
              MOV  AH, 02H
              MOV  DL, 0AH
              INT  21H
              MOV  DL, 0DH
              INT  21H
              MOV  AX, a
              MOV  DX, 0
              MOV  BX, b
              DIV  BX
              MOV  c, AX
              MOV  [msg + 0], 'L'
              MOV  [msg + 1], 'a'
              MOV  [msg + 2], ' '
              MOV  [msg + 3], 'd'
              MOV  [msg + 4], 'i'
              MOV  [msg + 5], 'v'
              MOV  [msg + 6], 'i'
              MOV  [msg + 7], 's'
              MOV  [msg + 8], 'i'
              MOV  [msg + 9], 'o'
              MOV  [msg + 10], 'n'
              MOV  [msg + 11], ' '
              MOV  [msg + 12], 'e'
              MOV  [msg + 13], 's'
              MOV  [msg + 14], ':'
              MOV  [msg + 15], '$'
              MOV  AH, 09H
              LEA  DX, msg
              INT  21H
              MOV  AH, 02H
              MOV  DL, 0AH
              INT  21H
              MOV  DL, 0DH
              INT  21H
              MOV  AX, c
              MOV  BX, 10
              MOV  CX, 0
  PUSHNUM3:   
              MOV  DX, 0
              DIV  BX
              ADD  DL, 48
              PUSH DX
              INC  CX
              CMP  AX, 0
              JNE  PUSHNUM3
  MOSTRARNUM3:
              MOV  DX, 0
              POP  DX
              MOV  AH, 02H
              INT  21H
              LOOP MOSTRARNUM3
              MOV  AH, 02H
              MOV  DL, 0AH
              INT  21H
              MOV  DL, 0DH
              INT  21H
              MOV  AH, 4CH
              INT  21H
MAIN ENDP
END MAIN                                   
