INSERT INTO public.perils (peril_name, description, scale_name, min_magnitude, max_magnitude)
VALUES ('Earthquake',
        'a sudden and violent shaking of the ground, sometimes causing great destruction, as a result of movements within the earth''s crust or volcanic action.',
        'Richter Scale',
        1.0, 10);

INSERT INTO public.perils (peril_name, description, scale_name, min_magnitude, max_magnitude)
VALUES ('Tornado',
        'a violent destructive whirling wind accompanied by a funnel-shaped cloud that progresses in a narrow path over the land.',
        'Enhanced Fujita Scale',
        0, 5);

INSERT INTO public.perils (peril_name, description, scale_name, min_magnitude, max_magnitude)
VALUES ('Volcanic Eruption',
        'an eruption of molten rock, hot rock fragments, and hot gases through a volcano.',
        'Volcanic Explosivity Index',
        0, 8);

INSERT INTO public.perils (peril_name, description, scale_name, min_magnitude, max_magnitude)
VALUES ('Tsunami',
        'a long high sea wave caused by an earthquake, submarine landslide, or other disturbance.',
        'Tsunami Intensity Scale',
        1, 12);

INSERT INTO public.perils (peril_name, description, scale_name, min_magnitude, max_magnitude)
VALUES ('Hail',
        'precipitation in the form of small balls or lumps usually consisting of concentric layers of clear ice and compact snow.',
        'Torro Hailstorm Intensity Scale',
        0, 10);
