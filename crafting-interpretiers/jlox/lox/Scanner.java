package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.craftinginterpreters.lox.TokenType.*;

public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private static final Map<String, TokenType> keywords;
    private int start = 0;
    private int current = 0;
    private int line = 1;

    static {
        keywords = new HashMap<>();
        keywords.put("and", AND);
        keywords.put("class", CLASS);
        keywords.put("else", ELSE);
        keywords.put("false", FALSE);
        keywords.put("for", FOR);
        keywords.put("fun", FUN);
        keywords.put("if", IF);
        keywords.put("nil", NIL);
        keywords.put("or", OR);
        keywords.put("print", PRINT);
        keywords.put("return", RETURN);
        keywords.put("super", SUPER);
        keywords.put("this", THIS);
        keywords.put("true", TRUE);
        keywords.put("var", VAR);
        keywords.put("while", WHILE);
    }

    Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            Logger.print("start: " + start + " current: " + current + " line: " + line);
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        Logger.print("char: '" + (c == '\n' ? "\\n" : c) + "'");

        char next = peek();

        switch (c) {
            case '(':
                Logger.print("'" + c + "' is a LEFT_PAREN token.");
                addToken(LEFT_PAREN);
                break;
            case ')':
                Logger.print("'" + c + "' is a RIGHT_PAREN token.");
                addToken(RIGHT_PAREN);
                break;
            case '{':
                Logger.print("'" + c + "' is a LEFT_BRACE token.");
                addToken(LEFT_BRACE);
                break;
            case '}':
                Logger.print("'" + c + "' is a RIGHT_BRACE token.");
                addToken(RIGHT_BRACE);
                break;
            case ',':
                Logger.print("'" + c + "' is a COMMA token.");
                addToken(COMMA);
                break;
            case '.':
                Logger.print("'" + c + "' is a DOT token.");
                addToken(DOT);
                break;
            case '-':
                Logger.print("'" + c + "' is a MINUS token.");
                addToken(MINUS);
                break;
            case '+':
                Logger.print("'" + c + "' is a PLUS token.");
                addToken(PLUS);
                break;
            case ';':
                Logger.print("'" + c + "' is a SEMICOLON token.");
                addToken(SEMICOLON);
                break;
            case '*':
                Logger.print("'" + c + "' is a STAR token.");
                addToken(STAR);
                break;
            case '!':
                Logger.print("next char: '" + next + "'");
                if (match('=')) {
                    Logger.print("'" + c + next + "' is a BANG_EQUAL token.");
                } else {
                    Logger.print("'" + c + "' is a BANG token.");
                }

                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                Logger.print("next char: '" + next + "'");
                if (match('=')) {
                    Logger.print("'" + c + next + "' is a EQUAL_EQUAL token.");
                } else {
                    Logger.print("'" + c + "' is a EQUAL token.");
                }

                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                Logger.print("next char: '" + next + "'");
                if (next == '=') {
                    Logger.print("'" + c + next + "' is a LESS_EQUAL token.");
                } else {
                    Logger.print("'" + c + "' is a LESS token.");
                }

                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                Logger.print("next char: '" + next + "'");
                if (next == '=') {
                    Logger.print("'" + c + next + "' is a GREATER_EQUAL token.");
                } else {
                    Logger.print("'" + c + "' is a GREATER token.");
                }

                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            case '/':
                Logger.print("next char: '" + next + "'");
                if (match('/')) {
                    Logger.print("'" + c + next + "' is a COMMENT token.");
                    Logger.print("Skipping comment.");
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    Logger.print("'" + c + "' is a SLASH token.");
                    addToken(SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                Logger.print("'\\n' is a NEWLINE token.");
                line++;
                break;
            case '"':
                string();
                break;
            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    Lox.error(line, "Unexpected character.");
                }

                break;
        }
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();

        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) type = IDENTIFIER;

        Logger.print("'" + text + "' is a " + (type.equals(IDENTIFIER) ? "IDENTIFIER" : type) + " token.");
        addToken(type);
    }

    private void number() {
        while (isDigit(peek())) advance();

        if (peek() == '.' && isDigit(peekNext())) {
            advance();

            while (isDigit(peek())) advance();
        }

        Logger.print("'" + source.substring(start, current) + "' is a NUMBER token.");

        addToken(NUMBER,
                Double.parseDouble(source.substring(start, current)));
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        if (isAtEnd()) {
            Lox.error(line, "Unterminated string.");
            return;
        }

        advance();

        String value = source.substring(start + 1, current - 1);
        Logger.print("'" + value + "' is a STRING token.");
        addToken(STRING, value);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'X') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
