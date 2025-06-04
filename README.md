# Compilador Tec++

**Tec++** es un compilador escrito en Java diseñado desde cero bajo la arquitectura por capas MVC, el cual implementa todas las etapas esenciales del proceso de compilación: análisis léxico, sintáctico, semántico, generación de código intermedio y traducción final a lenguaje ensamblador Intel 8086.

## Características

- ✅ Analizador léxico
- ✅ Analizador sintáctico (basado en una gramática personalizada)
- ✅ Analizador semántico (verificación de tipos, declaraciones, etc.)
- ✅ Generador de código intermedio
- ✅ Generador de código objeto
- ✅ Mensajes de error detallados para facilitar el desarrollo

## 📘 Gramática del Lenguaje

_A continuación se presenta una versión simplificada de la gramática del lenguaje Tec++:_

```
PROGRAMA -> ITC LISTA_DECL TECNM
LISTA_DECL -> [[DECL;]]*
DECL -> TIPO_DATO IDENTIFICADOR | IDENTIFICADOR = [[EXPRESION | TEXTO]] | CONDICIONAL | BUCLE | ENTRADA | SALIDA
TIPO_DATO -> ent | fracc | cad
CONDICIONAL -> si(EXPRESION_COMP)[ LISTA_DECL ] [[ sino[LISTA_DECL] | ε ]]
ENTRADA -> LEER(IDENTIFICADOR)
SALIDA -> MOSTRAR([[IDENTIFICADOR | TEXTO]])
BUCLE -> mientras(EXPRESION_COMP)[ LISTA_DECL ]
EXPRESION -> IDENTIFICADOR | NUMERO | DECIMAL | (EXPRESION OPER_ARIT EXPRESION)
EXPRESION_COMP -> EXPRESION OPER_COMP EXPRESION
IDENTIFICADOR -> LETRA [[LETRA|DIGITO]]*
OPER_ARIT-> +|-|*|/
OPER_COMP -> ==|!=|>|<|>=|<=
TEXTO -> ‘CARACTER*’
DECIMAL -> NUMERO.NUMERO
NUMERO -> DIGITO [[DIGITO]]*
LETRA -> A|B|C|…|Z|a|b|c|…|z
CARACTER -> LETRA|DIGITO|!|$|#|$|%|…|?
DIGITO -> 0|1|2|…|9
```

## 🧪 Código de Ejemplo

A continuación se muestra un ejemplo de un programa válido en el lenguaje **Tec++**:

```tecpp
ITC
  cad nombre;
  nombre = 'Carlos';
  fracc altura;
  altura = 1.82;
  ent calificacion;
  MOSTRAR('Ingresa tu calificacion');
  LEER(calificacion);
  si(calificacion>=70)[
    MOSTRAR('Aprobaste');
  ]sino[
    MOSTRAR('Reprobaste');
  ]
TECNM
```