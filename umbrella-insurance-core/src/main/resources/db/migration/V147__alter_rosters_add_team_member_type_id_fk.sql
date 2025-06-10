ALTER TABLE public.rosters ADD CONSTRAINT
rosters_team_member_type_id_fk FOREIGN KEY 
(team_member_type_id) REFERENCES public.team_member_types(team_member_type_id) 
ON DELETE SET NULL ON UPDATE SET NULL;