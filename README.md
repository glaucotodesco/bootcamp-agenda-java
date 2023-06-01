
# Steps
    1. Create a project with web, jpa, devtools and h2 dependencies
    2. Save in repo
    2.1 Using Git Conventions:

    <type>(<scope>): <subject>
    Aqui está o significado de cada parte:

        <type>: Indica o tipo de alteração realizada. Alguns exemplos comuns incluem:

            feat: Para uma nova funcionalidade adicionada.
            fix: Para uma correção de bug.
            docs: Para alterações na documentação.
            style: Para alterações na formatação do código.
            refactor: Para alterações que não adicionam novas funcionalidades ou corrigem bugs.
            test: Para adicionar ou modificar testes.
            chore: Para alterações de tarefas de build, configuração ou ferramentas.
            perf: Para melhorias de desempenho.
            <scope> (opcional): Indica o escopo da alteração, como o nome do módulo, arquivo ou componente afetado.

            <subject>: É uma descrição curta e objetiva do que foi alterado. Use verbos no tempo presente, na forma imperativa.


    3. Create Entities.
    4. Start Step-by-step mapping the entities using  @Transient
    
    5. Create Runners to Test Each new Mapping, test and refactoring each new mapping.
    
    6. Create profiles runner and test. User @Profile in Runner profile
    
    7. Create a script via H2-Console: SCRIPT TO 'c:\temp\dump.sql'

    8. Test Both Profile

    9. Create AreaController get /areas and test. (Copy All from other project!!!)

    10. Create Talent API Test Project.

    11. Create Environment and Variable Host
