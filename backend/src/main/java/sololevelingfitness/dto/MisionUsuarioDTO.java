package sololevelingfitness.dto;

public record MisionUsuarioDTO(
        Long misionId,
        String titulo,
        int meta,
        int progreso,
        boolean completado
) {}
